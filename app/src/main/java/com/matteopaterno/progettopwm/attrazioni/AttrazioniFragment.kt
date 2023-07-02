package com.matteopaterno.progettopwm.attrazioni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentAttrazioniBinding

class AttrazioniFragment : Fragment() {
    private lateinit var binding: FragmentAttrazioniBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAttrazioniBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_attrazioni, container, false)
    }

}