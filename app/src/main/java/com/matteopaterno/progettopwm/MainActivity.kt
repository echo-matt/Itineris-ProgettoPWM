package com.matteopaterno.progettopwm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.matteopaterno.progettopwm.databinding.ActivityMainBinding
import com.matteopaterno.progettopwm.loginregister.LoginFragment
import com.matteopaterno.progettopwm.loginregister.RegisterFragment


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
                transaction.commit()
            }
            transaction.show(loginFragment)

        }
        binding.registerButton.setOnClickListener {
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            if (!registerFragment.isAdded){
                transaction.setReorderingAllowed(true)
                transaction.replace(R.id.fragmentLoginRegistrationView, registerFragment)
                transaction.addToBackStack("Register Fragment")
                transaction.commit()
            }
            transaction.show(registerFragment)
        }
        setContentView(binding.root)
    }

    override fun onBackPressed() {
        val fragmentManger = supportFragmentManager
        val entryInBackStack = fragmentManger.backStackEntryCount
        for (entry in 0 until entryInBackStack){
            fragmentManger.popBackStack()
        }
        printBackStack("a", entryInBackStack)
    }

    private fun printBackStack(text: String, entryInBackStack: Int){
        var showMyFragments: String = text
        showMyFragments += "Number of Fragments: $entryInBackStack"
        Toast.makeText(this, showMyFragments, Toast.LENGTH_SHORT).show()
    }





}