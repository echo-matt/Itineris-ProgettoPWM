package com.matteopaterno.progettopwm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.matteopaterno.progettopwm.databinding.ActivityMainBinding
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val loginFragment = LoginFragment()
        val registerFragment = RegisterFragment()

        binding.loginButton.setOnClickListener {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            if (!loginFragment.isAdded){
                transaction.setReorderingAllowed(true)
                transaction.replace(R.id.fragmentLoginRegistrationView, loginFragment)
                transaction.addToBackStack("Login Fragment")
            }
            transaction.show(loginFragment)
            transaction.commit()
        }
        binding.registerButton.setOnClickListener {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            if (!registerFragment.isAdded){
                transaction.setReorderingAllowed(true)
                transaction.replace(R.id.fragmentLoginRegistrationView, registerFragment)
                transaction.addToBackStack("Login Fragment")
            }
            transaction.show(registerFragment)
            transaction.commit()
        }
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0){
            supportFragmentManager.popBackStack(null, POP_BACK_STACK_INCLUSIVE)
        }else {
            super.onBackPressed()
        }
    }

}