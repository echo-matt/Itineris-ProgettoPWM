package com.matteopaterno.progettopwm.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.matteopaterno.progettopwm.PrenotazioneFragment
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliHotelBinding

class DettagliHotelFragment : Fragment() {

    private lateinit var binding: FragmentDettagliHotelBinding
    private var hotel: HotelData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDettagliHotelBinding.inflate(layoutInflater)

        binding.textNome.text = hotel?.nome
        binding.textPosizione.text = hotel?.posizione
        binding.textCitta.text = hotel?.citta
        binding.ratingBar.rating = hotel?.rating!!

        binding.bottonePrenota.setOnClickListener{
            val fragment = PrenotazioneFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack("Fragment Prenotazioni")
                .commit()
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotel?.let { HotelDataListHolder.hotelDataList }

    }

    fun setHotel(hotel: HotelData){
        this.hotel = hotel
    }

    companion object{
        fun newInstance(hotel: HotelData): DettagliHotelFragment{
            val fragment = DettagliHotelFragment()
            fragment.setHotel(hotel)
            return fragment
        }
    }

}