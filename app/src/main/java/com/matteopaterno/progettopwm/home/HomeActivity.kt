package com.matteopaterno.progettopwm.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.matteopaterno.progettopwm.MainActivity
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.attrazioni.AttrazioniFragment
import com.matteopaterno.progettopwm.databinding.ActivityProfileBinding
import com.matteopaterno.progettopwm.databinding.FragmentAttrazioniBinding
import com.matteopaterno.progettopwm.hotel.HotelFragment
import com.matteopaterno.progettopwm.meteo.MeteoFragment
import com.matteopaterno.progettopwm.profile.ProfileFragment
import com.matteopaterno.progettopwm.ristoranti.RistorantiFragment

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding : ActivityProfileBinding


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        drawerLayout = binding.drawerLayout

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val navigationView = binding.nav
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,
            R.string.open_nav,
            R.string.close_nav
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HomeFragment()).commit()
            navigationView.setCheckedItem(R.id.home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding = ActivityProfileBinding.inflate(layoutInflater)
        when (item.itemId) {
            R.id.ristoranti -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, RistorantiFragment()).commit()
            R.id.home -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HomeFragment()).commit()
            R.id.hotel -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, HotelFragment()).commit()
            R.id.attrazzioni -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, AttrazioniFragment()).commit()
            R.id.meteo -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, MeteoFragment()).commit()
            R.id.exit -> logoutUser()
            R.id.profilo -> supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, ProfileFragment()).commit()
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logoutUser() {
        val loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE)
        val loginPrefsEditor = loginPreferences.edit()

        loginPrefsEditor.clear()
        loginPrefsEditor.putBoolean("saveLogin", false)
        loginPrefsEditor.putBoolean("isLoggedIn", false)
        loginPrefsEditor.apply()

        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}