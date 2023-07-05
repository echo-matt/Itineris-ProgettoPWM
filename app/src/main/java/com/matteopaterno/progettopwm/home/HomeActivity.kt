package com.matteopaterno.progettopwm.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matteopaterno.progettopwm.MainActivity
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.attrazioni.AttrazioniFragment
import com.matteopaterno.progettopwm.databinding.ActivityHomeBinding
import com.matteopaterno.progettopwm.hotel.HotelFragment
import com.matteopaterno.progettopwm.meteo.MeteoFragment
import com.matteopaterno.progettopwm.profile.ProfileFragment
import com.matteopaterno.progettopwm.ristoranti.RistorantiFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav
        navView.setOnItemSelectedListener(onNavigationItemSelectedListener)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HomeFragment()).commit()
            navView.selectedItemId = R.id.home
        }
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.ristoranti ->{
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, RistorantiFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.meteo ->{
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, MeteoFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }


            R.id.home ->{
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, HomeFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }


            R.id.hotel -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, HotelFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.attrazioni -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, AttrazioniFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.meteo -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, MeteoFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }

            R.id.profilo -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, ProfileFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


}