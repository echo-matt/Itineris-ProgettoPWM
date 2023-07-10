package com.matteopaterno.progettopwm.prenotazioni

import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
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
        val prenotazioniHotel = ManagerCarrello.getPrenotazioni()

        populateTable(prenotazioniHotel)


        return binding.root
    }

    private fun populateTable(prenotazioni: List<PrenotazioneData>) {
        binding.tablePrenotazioni.removeAllViews()

        if (prenotazioni.isNotEmpty()) {
            val headerRow = TableRow(requireContext())

            val headerIdTextView = TextView(context)
            headerIdTextView.text = "ID"
            val headerTipoTextView = TextView(context)
            headerTipoTextView.text = "Tipo"
            val headerNomeTextView = TextView(context)
            headerNomeTextView.text = "Nome"
            val headerInfoTextView = TextView(context)
            headerInfoTextView.text = "Info"

            val params = TableRow.LayoutParams(
                0,
                TableRow.LayoutParams.WRAP_CONTENT,
                1f
            )

            headerIdTextView.layoutParams = params
            headerIdTextView.setTypeface(null, Typeface.BOLD)
            headerIdTextView.setTextColor(resources.getColor(R.color.white))

            headerTipoTextView.layoutParams = params
            headerTipoTextView.setTypeface(null, Typeface.BOLD)
            headerTipoTextView.setTextColor(resources.getColor(R.color.white))

            headerNomeTextView.layoutParams = params
            headerNomeTextView.setTypeface(null, Typeface.BOLD)
            headerNomeTextView.setTextColor(resources.getColor(R.color.white))

            headerInfoTextView.layoutParams = params
            headerInfoTextView.setTypeface(null, Typeface.BOLD)
            headerInfoTextView.setTextColor(resources.getColor(R.color.white))

            headerRow.addView(headerIdTextView)
            headerRow.addView(headerTipoTextView)
            headerRow.addView(headerNomeTextView)
            headerRow.addView(headerInfoTextView)

            val bgColor = ContextCompat.getColor(requireContext(), R.color.blue)
            setTableRowBackground(headerRow, bgColor)

            binding.tablePrenotazioni.addView(headerRow)

            for (item in prenotazioni) {
                val newRow = TableRow(requireContext())

                val itemIdTextView = TextView(context)
                itemIdTextView.text = item.id.toString()
                val itemTipoTextView = TextView(context)
                itemTipoTextView.text = when (item.tipoPrenotazione) {
                    TipoPrenotazione.HOTEL -> "Hotel"
                    TipoPrenotazione.RISTORANTE -> "Restaurant"
                }
                val itemNomeTextView = TextView(context)
                itemNomeTextView.text = item.nome
                val itemInfoTextView = TextView(context)

                val params = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )

                itemIdTextView.layoutParams = params
                itemTipoTextView.layoutParams = params
                itemNomeTextView.layoutParams = params
                itemInfoTextView.layoutParams = params

                itemInfoTextView.setTypeface(null, Typeface.BOLD)

                if (item.tipoPrenotazione == TipoPrenotazione.HOTEL) {
                    itemInfoTextView.text = "Check-in: ${item.checkInDate}\nCheck-out: ${item.checkOutDate}"
                } else if (item.tipoPrenotazione == TipoPrenotazione.RISTORANTE) {
                    itemInfoTextView.text = "Reservation Time: ${item.orarioPrenotazione}"
                }

                newRow.addView(itemIdTextView)
                newRow.addView(itemTipoTextView)
                newRow.addView(itemNomeTextView)
                newRow.addView(itemInfoTextView)

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