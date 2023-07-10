package com.matteopaterno.progettopwm.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.attrazioni.AttrazioniDataDBRequest
import com.matteopaterno.progettopwm.attrazioni.AttrazioniFragment
import com.matteopaterno.progettopwm.databinding.ActivityHomeBinding
import com.matteopaterno.progettopwm.hotel.HotelDataDBRequest
import com.matteopaterno.progettopwm.hotel.HotelFragment
import com.matteopaterno.progettopwm.profile.ProfileFragment
import com.matteopaterno.progettopwm.recensioni.RecensioniHotelDataDBRequest
import com.matteopaterno.progettopwm.ristoranti.RistorantiDataDBRequest
import com.matteopaterno.progettopwm.ristoranti.RistorantiFragment

class HomeActivity : AppCompatActivity(){
    private lateinit var binding: ActivityHomeBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        val loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.bottomNav
        navView.setOnItemSelectedListener(onNavigationItemSelectedListener)
        supportFragmentManager.beginTransaction().replace(binding.fragmentContainer.id, HomeFragment()).commit()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HomeFragment()).commit()
            navView.selectedItemId = R.id.home
        }

        val hotelDataRepo = HotelDataDBRequest()
        hotelDataRepo.createHotelList {  } //Creo la lista degli hotel quando starta l'activity

        val ristorantiDataRepo = RistorantiDataDBRequest()
        ristorantiDataRepo.createRistorantiList {  }

        val recensioniHotelDataRepo = RecensioniHotelDataDBRequest()
        recensioniHotelDataRepo.createRecensioniList { }

        val attrazioniDataHotelRepo = AttrazioniDataDBRequest()
        attrazioniDataHotelRepo.createAttrazioniList {  }

    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.ristoranti ->{
                val transaction = supportFragmentManager.beginTransaction()
                transaction.setReorderingAllowed(true)
                transaction.replace(binding.fragmentContainer.id, RistorantiFragment()).commit()
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

            R.id.profilo -> {
                supportFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainer.id, ProfileFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}