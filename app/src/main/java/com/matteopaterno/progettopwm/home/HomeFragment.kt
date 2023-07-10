package com.matteopaterno.progettopwm.home

import AttrazioniData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.attrazioni.AttrazioniAdapter
import com.matteopaterno.progettopwm.attrazioni.AttrazioniDataListHolder
import com.matteopaterno.progettopwm.attrazioni.DettagliAttrazioniFragment
import com.matteopaterno.progettopwm.databinding.FragmentHomeBinding
import com.matteopaterno.progettopwm.hotel.DettagliHotelFragment
import com.matteopaterno.progettopwm.hotel.HotelAdapter
import com.matteopaterno.progettopwm.hotel.HotelData
import com.matteopaterno.progettopwm.hotel.HotelDataListHolder
import com.matteopaterno.progettopwm.ristoranti.DettagliRistorantiFragment
import com.matteopaterno.progettopwm.ristoranti.RistorantiAdapter
import com.matteopaterno.progettopwm.ristoranti.RistorantiData
import com.matteopaterno.progettopwm.ristoranti.RistorantiDataListHolder

class HomeFragment : Fragment(), HotelAdapter.OnItemClickListener, RistorantiAdapter.OnItemClickListener, AttrazioniAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var ristorantiAdapter: RistorantiAdapter
    private lateinit var attrazioniAdapter: AttrazioniAdapter


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)


        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        hotelAdapter = HotelAdapter(HotelDataListHolder.hotelDataList)
        hotelAdapter.setOnItemClickListener(this)
        binding.hotelRecyclerView.adapter = hotelAdapter


        binding.ristorantiRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        ristorantiAdapter = RistorantiAdapter(RistorantiDataListHolder.RistorantiDataList)
        ristorantiAdapter.setOnItemClickListener(this)
        binding.ristorantiRecyclerView.adapter = ristorantiAdapter

        binding.attrazioniRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        attrazioniAdapter = AttrazioniAdapter(AttrazioniDataListHolder.AttrazioniDataList)
        attrazioniAdapter.setOnItemClickListener(this)
        binding.attrazioniRecyclerView.adapter = attrazioniAdapter


        return binding.root
    }

    override fun onItemClick(hotel: HotelData) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val fragment = DettagliHotelFragment.newInstance(hotel)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right, // Animazione di transizione in entrata per DettagliRistorantiFragment
            R.anim.slide_out_left, // Animazione di transizione in uscita per RistorantiFragment
            R.anim.slide_in_left, // Animazione di transizione in entrata per RistorantiFragment
            R.anim.slide_out_right // Animazione di transizione in uscita per DettagliRistorantiFragment
        )


        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack("Dettagli Hotel")
            .commit()
    }

    override fun onItemClick(ristorante: RistorantiData) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val fragment = DettagliRistorantiFragment.newInstance(ristorante)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right, // Animazione di transizione in entrata per DettagliRistorantiFragment
            R.anim.slide_out_left, // Animazione di transizione in uscita per RistorantiFragment
            R.anim.slide_in_left, // Animazione di transizione in entrata per RistorantiFragment
            R.anim.slide_out_right // Animazione di transizione in uscita per DettagliRistorantiFragment
        )


        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack("Dettagli Hotel")
            .commit()
    }

    override fun onItemClick(attrazione: AttrazioniData) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        val fragment = DettagliAttrazioniFragment.newInstance(attrazione)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()

        transaction.setCustomAnimations(
            R.anim.slide_in_right, // Animazione di transizione in entrata per DettagliRistorantiFragment
            R.anim.slide_out_left, // Animazione di transizione in uscita per RistorantiFragment
            R.anim.slide_in_left, // Animazione di transizione in entrata per RistorantiFragment
            R.anim.slide_out_right // Animazione di transizione in uscita per DettagliRistorantiFragment
        )


        transaction.replace(R.id.fragment_container, fragment)
            .addToBackStack("Dettagli Hotel")
            .commit()
    }

}