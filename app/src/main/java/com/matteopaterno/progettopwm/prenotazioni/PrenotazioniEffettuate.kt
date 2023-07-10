package com.matteopaterno.progettopwm.prenotazioni

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.databinding.FragmentPrenotazioniEffettuateBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PrenotazioniEffettuate : Fragment() {
    private lateinit var binding : FragmentPrenotazioniEffettuateBinding
    private lateinit var tabellaPrenotazioniEffettuate : TableLayout
    private lateinit var sharedPreferences: SharedPreferences
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getString("id", "")!!.toInt()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPrenotazioniEffettuateBinding.inflate(layoutInflater)
        tabellaPrenotazioniEffettuate = binding.tabellaPrenotazioniEffettuate
        caricaPrenotazioni()
        return binding.root
    }

    private fun caricaPrenotazioni() {

        val query = """
        SELECT * FROM (
            SELECT 'hotel' AS type, id, hotel_id AS entity_id, check_in_date, check_out_date, guests, citta
            FROM hotel_reservations
            WHERE user_id = $userId
            UNION ALL
            SELECT 'restaurant' AS type, id, ristorante_id AS entity_id, reservation_date, reservation_time, guests
            FROM restaurant_reservations
            WHERE user_id = $userId
        ) AS reservations
        LEFT JOIN hotels ON reservations.entity_id = hotels.id
        LEFT JOIN restaurants ON reservations.entity_id = restaurants.id
    """.trimIndent()

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        val jsonResponse = response.body()
                        val queryset = jsonResponse?.getAsJsonArray("queryset")
                        queryset?.let {
                            val prenotazioni = ottieniPrenotazioni(queryset)
                            popolaTabella(prenotazioni)
                        }
                    }else{

                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun popolaTabella(prenotazioni: List<PrenotazioneData>) {
        for (prenotazione in prenotazioni){
            val row = TableRow(requireContext())

            val idTextView = TextView(requireContext())
            idTextView.text = prenotazione.id.toString()
            val typeTextView = TextView(requireContext())
            val nameTextView = TextView(requireContext())
            val infoTextView = TextView(requireContext())

            if (prenotazione.tipoPrenotazione == TipoPrenotazione.HOTEL ) {
                typeTextView.text = "Hotel"
                nameTextView.text = prenotazione.nome
                infoTextView.text = "Check-in: ${prenotazione.checkInDate}\nCheck-out: ${prenotazione.checkOutDate}"
            } else if (prenotazione.tipoPrenotazione == TipoPrenotazione.RISTORANTE) {
                typeTextView.text = "Ristorante"
                nameTextView.text = prenotazione.nome
                infoTextView.text = "Data prenotazione: ${prenotazione.dataPrenotazione}\nOrario prenotazione: ${prenotazione.orarioPrenotazione}"
            }

            row.addView(idTextView)
            row.addView(typeTextView)
            row.addView(nameTextView)
            row.addView(infoTextView)

            tabellaPrenotazioniEffettuate.addView(row)
        }

    }

    private fun ottieniPrenotazioni(queryset: JsonArray): List<PrenotazioneData> {
        val prenotazioni = mutableListOf<PrenotazioneData>()

        for (i in 0 until queryset.size()){
            val jsonObject = queryset.get(i).asJsonObject
            val type = jsonObject.get("type").asString

            val id = jsonObject.get("id").asInt
            val entityID = jsonObject.get("entity_id").asInt
            val checkInDate = jsonObject.get("check_in_date").asString
            val checkOutDate = jsonObject.get("check_out_date").asString
            val guests = jsonObject.get("guests").asInt
            val citta = jsonObject.get("citta").asString

            val prenotazione: PrenotazioneData = if (type == "hotel") {
                val hotelName = jsonObject.get("hotel_name").asString
                PrenotazioneData(
                    id = id,
                    idHotel = entityID,
                    checkInDate = checkInDate,
                    checkOutDate = checkOutDate,
                    hotelGuests = guests,
                    nome = hotelName,
                    citta = citta,
                    tipoPrenotazione = TipoPrenotazione.HOTEL)
            } else {
                val restaurantName = jsonObject.get("restaurant_name").asString
                val reservationDate = jsonObject.get("reservation_date").asString
                val reservationTime = jsonObject.get("reservation_time").asString
                PrenotazioneData(
                    id = id,
                    idRistorante = entityID,
                    dataPrenotazione = reservationDate,
                    restaurantGuests = guests,
                    nome = restaurantName,
                    orarioPrenotazione = reservationTime,
                    tipoPrenotazione = TipoPrenotazione.RISTORANTE)
            }

            prenotazioni.add(prenotazione)
        }
        return prenotazioni
    }

}