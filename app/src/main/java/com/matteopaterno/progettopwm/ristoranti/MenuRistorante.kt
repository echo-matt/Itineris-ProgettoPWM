package com.matteopaterno.progettopwm.ristoranti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentMenuRistoranteBinding

class MenuRistorante : Fragment() {
    private lateinit var binding: FragmentMenuRistoranteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMenuRistoranteBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        return binding.root
    }

}