package com.matteopaterno.progettopwm.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopaterno.progettopwm.databinding.FragmentAttrazioniBinding
import com.matteopaterno.progettopwm.databinding.FragmentHomeBinding
import com.matteopaterno.progettopwm.hotel.HotelAdapter
import com.matteopaterno.progettopwm.ristoranti.RistorantiAdapter
import com.matteopaterno.progettopwm.ristoranti.RistorantiFragment

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hotelRecyclerView.adapter
        binding.ristorantiRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.ristorantiRecyclerView.adapter
        // TODO IMPLEMENTARE STESSA RECYCLE VIEW DEI 2 FRAGMENT

        return binding.root
    }



}