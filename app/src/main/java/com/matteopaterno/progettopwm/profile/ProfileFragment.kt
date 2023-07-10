package com.matteopaterno.progettopwm.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matteopaterno.progettopwm.MainActivity
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentProfileBinding
import com.matteopaterno.progettopwm.prenotazioni.CarrelloFragment
import com.matteopaterno.progettopwm.prenotazioni.PrenotazioniEffettuate
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var loginPreferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val URL = loginPreferences.getString("img", "")
        setProfileImg(URL)
    }

    private fun setProfileImg(URL: String?) {
        ClientNetwork.retrofit.image(URL!!).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val avatar: Bitmap? = BitmapFactory.decodeStream(
                            ByteArrayInputStream(
                                response.body()!!.bytes()
                            )
                        )
                        binding.imageView3.setImageBitmap(avatar)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    t.printStackTrace()
                    Toast.makeText(activity, "Richiesta fallita", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)

        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE)

        binding.nomeTextView.text = loginPreferences.getString("nome", "")

        binding.settingsButton.setOnClickListener {
            loginPreferences.edit().remove("img").apply()
        }
        binding.carrelloButton.setOnClickListener {

            activity?.supportFragmentManager
            ?.beginTransaction()
            ?.replace(R.id.fragment_container, CarrelloFragment())
            ?.addToBackStack("Fragment carrello")
                ?.commit()

        }

        binding.logoutButton.setOnClickListener {
            logoutUser()
        }

        binding.buttonPrenotazioni.setOnClickListener {
            val fragment = PrenotazioniEffettuate()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment).commit()
        }

        return binding.root
    }

    private fun logoutUser() {

        val loginPreferences =  activity?.getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val loginPrefsEditor = loginPreferences?.edit()

        loginPrefsEditor?.clear()
        loginPrefsEditor?.putBoolean("saveLogin", false)
        loginPrefsEditor?.putBoolean("isLoggedIn", false)
        loginPrefsEditor?.apply()

        val intent = Intent(activity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

}