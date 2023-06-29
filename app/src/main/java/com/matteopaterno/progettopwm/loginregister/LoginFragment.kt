package com.matteopaterno.progettopwm.loginregister

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.home.HomeActivity
import com.matteopaterno.progettopwm.databinding.FragmentLoginBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
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

        val query = "select * from users where username = '${username}' and password = '${password}';"

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if(response.isSuccessful){
                        if ((response.body()?.get("queryset") as JsonArray).size() == 1){
                            Toast.makeText(activity, "Select successful", Toast.LENGTH_SHORT).show()
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
    }
}