package com.matteopaterno.progettopwm.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.SCROLL_AXIS_HORIZONTAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.matteopaterno.progettopwm.databinding.FragmentHomeBinding
import com.matteopaterno.progettopwm.databinding.HotelCardViewDesignBinding
import com.matteopaterno.progettopwm.hotel.HotelDataListHolder
import com.matteopaterno.progettopwm.hotel.HotelAdapter
import com.matteopaterno.progettopwm.ristoranti.RistorantiAdapter
import com.matteopaterno.progettopwm.ristoranti.RistorantiDataListHolder

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)

        val hotelAdapter = HotelAdapter(HotelDataListHolder.hotelDataList)
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.hotelRecyclerView.adapter = hotelAdapter

        val ristorantiAdapter = RistorantiAdapter(RistorantiDataListHolder.RistorantiDataList)
        binding.ristorantiRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.ristorantiRecyclerView.adapter = ristorantiAdapter

        return binding.root
    }

}