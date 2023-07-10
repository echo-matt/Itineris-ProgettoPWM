package com.matteopaterno.progettopwm.prenotazioni

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.DialogPaymentBinding
import com.matteopaterno.progettopwm.databinding.FragmentCarrelloBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CarrelloFragment : Fragment() {

    private lateinit var binding: FragmentCarrelloBinding
    private lateinit var binding2: DialogPaymentBinding
    private var costo: Double = 0.0
    private var prenotazioni: List<PrenotazioneData> = listOf()
    private lateinit var loginPreferences : SharedPreferences
    private var idUtente: Int = 0
    private var totalCost: Double = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        idUtente = loginPreferences.getString("id", "")?.toInt()!!

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCarrelloBinding.inflate(layoutInflater)
        val prenotazioni = ManagerCarrello.getPrenotazioni()

        populateTable(prenotazioni)

        binding.button.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(requireContext())
            binding2 = DialogPaymentBinding.inflate(layoutInflater)

            val dialogView = binding2.root

            binding2.costoTotale.text = "Costo totale della prenotazione: $totalCost €"

            val amountText = binding2.editTextAmount
            dialogBuilder.setView(dialogView)
            dialogBuilder.setTitle("Pagamento")

            dialogBuilder.setPositiveButton("Paga"){_, _ ->
                val amount = amountText.text.toString()
                if (amount.isNotEmpty()){
                    val double = amount.toDouble()
                    if (double == totalCost){
                        Toast.makeText(context, "Pagamento effettuato, carrello svuotato", Toast.LENGTH_SHORT).show()
                        for (item in prenotazioni){
                            if (item.tipoPrenotazione == TipoPrenotazione.HOTEL){
                                effettuaPrenotazioneHotel(idUtente, item.idHotel, item.checkInDate, item.checkOutDate, item.hotelGuests)
                            }else{
                                effettuaPrenotazioneRistorante(idUtente, item.idRistorante, item.dataPrenotazione, item.restaurantGuests, item.orarioPrenotazione)
                            }
                        }
                        ManagerCarrello.svuotaCarrello()

                        val fragment = CarrelloFragment()
                        val transaction = requireActivity().supportFragmentManager.beginTransaction()

                        transaction.replace(R.id.fragment_container, fragment)
                            .commit()



                    }else if (double > totalCost!!){
                        Toast.makeText(context, "Inserisci l'importo esatto", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(context, "Pagamento rifiutato", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            dialogBuilder.setNegativeButton("Cancella"){dialog, _ ->
                dialog.dismiss()
            }

            dialogBuilder.create().show()
        }

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
            val headerCostoTextView = TextView(context)
            headerCostoTextView.text = "Costo"

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

            headerCostoTextView.layoutParams = params
            headerCostoTextView.setTypeface(null, Typeface.BOLD)
            headerCostoTextView.setTextColor(resources.getColor(R.color.white))

            headerRow.addView(headerIdTextView)
            headerRow.addView(headerTipoTextView)
            headerRow.addView(headerNomeTextView)
            headerRow.addView(headerInfoTextView)
            headerRow.addView(headerCostoTextView)

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
                    TipoPrenotazione.RISTORANTE -> "Ristorante"
                    else -> {
                        return
                    }
                }
                val itemNomeTextView = TextView(context)
                itemNomeTextView.text = item.nome
                val itemInfoTextView = TextView(context)

                costo = item.costoPrenotazione ?: 0.0

                val itemCostoTextView = TextView(context)
                itemCostoTextView.text = costo.toString()

                val params = TableRow.LayoutParams(
                    0,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )

                itemIdTextView.layoutParams = params
                itemTipoTextView.layoutParams = params
                itemNomeTextView.layoutParams = params
                itemInfoTextView.layoutParams = params
                itemCostoTextView.layoutParams = params


                itemInfoTextView.setTypeface(null, Typeface.BOLD)
                itemCostoTextView.setTypeface(null, Typeface.BOLD)

                if (item.tipoPrenotazione == TipoPrenotazione.HOTEL) {
                    itemInfoTextView.text =
                        "Check-in: ${item.checkInDate}\nCheck-out: ${item.checkOutDate}"
                } else if (item.tipoPrenotazione == TipoPrenotazione.RISTORANTE) {
                    itemInfoTextView.text = "Orario prenotazione: ${item.orarioPrenotazione}"
                }

                newRow.addView(itemIdTextView)
                newRow.addView(itemTipoTextView)
                newRow.addView(itemNomeTextView)
                newRow.addView(itemInfoTextView)
                newRow.addView(itemCostoTextView)

                binding.tablePrenotazioni.addView(newRow)

                totalCost += costo

            }
        } else {
            Toast.makeText(context, "Nessuna prenotazione", Toast.LENGTH_SHORT).show()
        }

        val totalCostRow = TableRow(requireContext())

        val totalCostLabelTextView = TextView(context)
        totalCostLabelTextView.text = "Costo totale:"

        val totalCostValueTextView = TextView(context)
        totalCostValueTextView.text = totalCost.toString()

        val params = TableRow.LayoutParams(
            0,
            TableRow.LayoutParams.WRAP_CONTENT,
            1f
        )

        totalCostLabelTextView.layoutParams = params
        totalCostLabelTextView.setTypeface(null, Typeface.BOLD)

        totalCostValueTextView.layoutParams = params
        totalCostValueTextView.setTypeface(null, Typeface.BOLD)

        totalCostRow.addView(totalCostLabelTextView)
        totalCostRow.addView(totalCostValueTextView)

        binding.tablePrenotazioni.addView(totalCostRow)


    }
    private fun effettuaPrenotazione(){

        for (item in prenotazioni){
            if (item.tipoPrenotazione == TipoPrenotazione.HOTEL){
                effettuaPrenotazioneHotel(idUtente, item.idHotel, item.checkInDate, item.checkOutDate, item.hotelGuests)
            }else{
                effettuaPrenotazioneRistorante(idUtente, item.idRistorante, item.dataPrenotazione, item.restaurantGuests, item.orarioPrenotazione)
            }
        }
    }

    private fun effettuaPrenotazioneRistorante(idUtente: Int, idRistorante: Int?, dataPrenotazione: String?, restaurantGuests: Int?, orarioPrenotazione: String?) {
        Log.v("test", "test")
        Log.v("idutente", idUtente.toString())
        Log.v("idristorante", idRistorante.toString())
        Log.v("dataprent", dataPrenotazione.toString())
        Log.v("guests", restaurantGuests.toString())
        Log.v("orario", orarioPrenotazione.toString())
        val query = "INSERT INTO webmobile.restaurant_reservation (user_id, ristorante_id, guests, reservation_date, reservation_time) VALUES ('${idUtente}', '${idRistorante}','${restaurantGuests}', '${dataPrenotazione}', '${orarioPrenotazione}')"

        ClientNetwork.retrofit.insert(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        Log.v("test", "no data")
                        Toast.makeText(activity, "Prenotazione effettuata", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Errore nella richiesta", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }

            }
        )
    }

    private fun effettuaPrenotazioneHotel(
        idUtente: Int?,
        idHotel: Int?,
        checkInDate: String?,
        checkOutDate: String?,
        hotelGuests: Int?, ) {
        val query = "INSERT INTO webmobile.hotel_reservations (user_id, hotel_id, check_in_date, check_out_date, guests, payment_status) VALUES ('${idUtente}', '${idHotel}', '${checkInDate.toString()}', '${checkOutDate.toString()}', '${hotelGuests}', 'Pagato')"

        ClientNetwork.retrofit.insert(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        Toast.makeText(activity, "Prenotazione effettuata", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(activity, "Errore nella richiesta", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }

            }
        )
    }

    private fun effettuaPrenotazioneHotel() {
        TODO("Not yet implemented")
    }
}





    private fun setTableRowBackground(tableRow: TableRow, color: Int) {
        val drawable: Drawable = GradientDrawable().apply {
            setColor(color)
        }
        tableRow.background = drawable
    }

