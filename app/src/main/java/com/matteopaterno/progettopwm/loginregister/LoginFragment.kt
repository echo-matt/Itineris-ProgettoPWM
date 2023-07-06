package com.matteopaterno.progettopwm.loginregister

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.databinding.FragmentLoginBinding
import com.matteopaterno.progettopwm.home.HomeActivity
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private lateinit var loginPreferences : SharedPreferences
    private lateinit var loginPrefsEditor : SharedPreferences.Editor
    private var saveLogin = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()

        saveLogin = loginPreferences.getBoolean("saveLogin", false)
        if (saveLogin){
            binding.username.setText(loginPreferences.getString("username", ""))
            binding.password.setText(loginPreferences.getString("password", ""))
            binding.rememberMeCheckbox.isChecked = true
        }

        binding.loginButton.setOnClickListener {

            val username = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (username.isEmpty()){
                binding.username.error = "Username richiesto"
                binding.username.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()){
                binding.password.error = "Password richiesta"
                binding.password.requestFocus()
                return@setOnClickListener
            }

            loginUtente(username, password)

        }
        return binding.root
    }

    private fun loginUtente(username: String, password: String){

        val query1 = "select * from users where username = '${username}' and password = '${password}';"

        ClientNetwork.retrofit.select(query1).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful){

                        if ((response.body()?.get("queryset") as JsonArray).size() == 1){
                            Toast.makeText(activity, "Select successful", Toast.LENGTH_SHORT).show()

                            val jsonResponse = response.body()
                            val queryset = jsonResponse?.getAsJsonArray("queryset")
                            val jsonObject = queryset!![0].asJsonObject
                            val nome = jsonObject?.get("nome")?.asString
                            val cognome = jsonObject?.get("cognome")?.asString
                            val id = jsonObject?.get("id")?.asString

                            //Se la checkbox e' selezionata, salva nelle shared preferences username, password, il voler essere ricordati, e lo stato di login
                            if (binding.rememberMeCheckbox.isChecked){
                                saveLoginInfo(username, password, nome, cognome, id)
                                loginPrefsEditor.putBoolean("saveLogin", true)
                                loginPrefsEditor.putBoolean("isLoggedIn", true)
                                loginPrefsEditor.apply()
                            }else{
                                saveLoginInfo(username, password, nome, cognome, id)
                                loginPrefsEditor.remove("saveLogin")
                                loginPrefsEditor.putBoolean("saveLogin", false)
                                loginPrefsEditor.putBoolean("isLoggedIn", true)
                                loginPrefsEditor.apply()
                            }

                            val intent = Intent(context, HomeActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }else{
                            Toast.makeText(activity, "Username o password errati", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Error failure", Toast.LENGTH_SHORT).show()
                }
            }
        )

        val query2 = "select img from users where username = '${loginPreferences.getString("username","matteop")}';"
        ClientNetwork.retrofit.select(query2).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse = response.body()
                        val queryset = jsonResponse?.getAsJsonArray("queryset")

                        if (queryset != null) {
                            val jsonObject = queryset[0].asJsonObject
                            val url = jsonObject?.get("img")?.asString
                            loginPreferences.edit().putString("img", url).apply()
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                    Toast.makeText(activity, "Richiesta fallita", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun saveLoginInfo(
        username: String,
        password: String,
        nome: String?,
        cognome: String?,
        id: String?
    ) {
        loginPrefsEditor.putString("username", username)
        loginPrefsEditor.putString("password", password)
        loginPrefsEditor.putString("nome", nome)
        loginPrefsEditor.putString("cognome", cognome)
        loginPrefsEditor.putString("id", id)
    }
}