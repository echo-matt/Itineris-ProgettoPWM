package com.matteopaterno.progettopwm.profile

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matteopaterno.progettopwm.MainActivity
import com.matteopaterno.progettopwm.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding : FragmentProfileBinding
    private lateinit var loginPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", MODE_PRIVATE)


        //TODO IMPLEMENTARE RICHIESTE AL DB PER SCARICARE IMMAGINE PROFILO E SETTARLA

        binding.logoutButton.setOnClickListener {
            logoutUser()
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