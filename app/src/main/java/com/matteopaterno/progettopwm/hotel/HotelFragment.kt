package com.matteopaterno.progettopwm.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentHotelBinding

class HotelFragment : Fragment() {
    private lateinit var binding : FragmentHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)

        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context)
        val data = ArrayList<HotelData>()
        for (i in 1..20){
            data.add(HotelData(R.drawable.photo_1506905925346_21bda4d32df4, "Hotel X", 4.0, "Via Roma 1"))
        }

        val adapter = HotelAdapter(data)
        binding.hotelRecyclerView.adapter = adapter

        return binding.root
    }

}