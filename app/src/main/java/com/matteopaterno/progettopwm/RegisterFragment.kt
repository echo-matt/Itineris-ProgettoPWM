package com.matteopaterno.progettopwm

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.databinding.FragmentRegisterBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import com.matteopaterno.progettopwm.retrofit.UserModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        binding.registerButton.setOnClickListener {
            val nome = binding.nome.text.toString()
            val cognome = binding.cognome.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val userDataObj = UserModel(nome, cognome, username, password)

            if (nome.isEmpty()){
                binding.nome.error = "Nome richiesto"
                binding.nome.requestFocus()
                return@setOnClickListener
            }
            if (cognome.isEmpty()){
                binding.cognome.error = "Cognome richiesto"
                binding.cognome.requestFocus()
                return@setOnClickListener
            }
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
            registraUtente(nome, cognome, username, password)
        }
        return binding.root
    }

    private fun registraUtente(
        nome: String,
        cognome: String,
        username: String,
        password: String
    ) {
        val query = "INSERT INTO webmobile.users (nome, cognome, username, password, img) VALUES ('${nome}', '${cognome}', '${username}', '${password}', NULL)"


        ClientNetwork.retrofit.insert(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        Toast.makeText(activity, "Utente registrato", Toast.LENGTH_SHORT).show()
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }else{
                        Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            }
        )
    }
}