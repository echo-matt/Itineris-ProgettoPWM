package com.matteopaterno.progettopwm.hotel

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentHotelBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelFragment : Fragment(){
    lateinit var binding : FragmentHotelBinding
    private lateinit var hotelAdapter: HotelAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHotelBinding.inflate(layoutInflater)

        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context)
        hotelAdapter = HotelAdapter(HotelDataListHolder.filteredHotelDataList)
        binding.hotelRecyclerView.adapter = hotelAdapter

        binding.filterText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filterText= s.toString().trim()
                hotelAdapter.filterRecyclerView(filterText)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return binding.root
    }
}