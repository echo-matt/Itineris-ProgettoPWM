package com.matteopaterno.progettopwm.prenotazioni

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentCarrelloBinding

class CarrelloFragment : Fragment() {

    private lateinit var binding : FragmentCarrelloBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarrelloBinding.inflate(layoutInflater)
        val prenotazioniHotel = ManagerCarrello.getPrenotazioniHotel()

        populateTable(prenotazioniHotel)

        binding.button.setOnClickListener {
            val f = PrenotazioneHotelFragment()
            for (item in prenotazioniHotel){
            }
        }

        return binding.root
    }

    private fun populateTable(prenotazioniHotel: List<HotelPrenotazioneData>) {
        binding.tablePrenotazioni.removeAllViews()
        if (prenotazioniHotel.size > 0) {

            val headerRow = TableRow(requireContext())
            val headerIdTextView = TextView(context)
            headerIdTextView.text = "ID"
            val headerNomeTextView = TextView(context)
            headerNomeTextView.text = "Nome Hotel"
            val headerCheckInTextView = TextView(context)
            headerCheckInTextView.text = "Check-In"
            val headerCheckOutTextView = TextView(context)
            headerCheckOutTextView.text = "Check-Out"

            val params = TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1f)

            headerIdTextView.layoutParams = params
            headerIdTextView.setTypeface(null, Typeface.BOLD)
            headerIdTextView.setTextColor(resources.getColor(R.color.white))

            headerNomeTextView.layoutParams = params
            headerNomeTextView.setTypeface(null, Typeface.BOLD)
            headerNomeTextView.setTextColor(resources.getColor(R.color.white))

            headerCheckInTextView.layoutParams = params
            headerCheckInTextView.setTypeface(null, Typeface.BOLD)
            headerCheckInTextView.setTextColor(resources.getColor(R.color.white))

            headerCheckOutTextView.layoutParams = params
            headerCheckOutTextView.setTypeface(null, Typeface.BOLD)
            headerCheckOutTextView.setTextColor(resources.getColor(R.color.white))

            headerRow.addView(headerIdTextView)
            headerRow.addView(headerNomeTextView)
            headerRow.addView(headerCheckInTextView)
            headerRow.addView(headerCheckOutTextView)

            val bgColor = ContextCompat.getColor(requireContext(), R.color.blue)
            setTableRowBackground(headerRow, bgColor)

            binding.tablePrenotazioni.addView(headerRow)

            for (item in prenotazioniHotel) {
                val newRow = TableRow(requireContext())

                val itemIdTextView = TextView(context)
                itemIdTextView.text = item.id.toString()
                val itemNomeTextView = TextView(context)
                itemNomeTextView.text = item.hotelNome
                val itemCheckInTextView = TextView(context)
                itemCheckInTextView.text = item.checkInDate
                val itemCheckOutTextView = TextView(context)
                itemCheckOutTextView.text = item.checkOutDate

                val params = TableRow.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    1f
                )
                itemIdTextView.layoutParams = params
                itemNomeTextView.layoutParams = params
                itemCheckInTextView.layoutParams = params
                itemCheckOutTextView.layoutParams = params

                newRow.addView(itemIdTextView)
                newRow.addView(itemNomeTextView)
                newRow.addView(itemCheckInTextView)
                newRow.addView(itemCheckOutTextView)

                binding.tablePrenotazioni.addView(newRow)

            }
        } else {
            Toast.makeText(context, "Nessuna prenotazione", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setTableRowBackground(tableRow: TableRow, color: Int) {
        val drawable: Drawable = GradientDrawable().apply {
            setColor(color)
        }
        tableRow.background = drawable
    }

}