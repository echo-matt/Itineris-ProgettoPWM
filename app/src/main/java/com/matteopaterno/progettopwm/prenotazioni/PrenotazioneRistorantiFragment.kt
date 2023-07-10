package com.matteopaterno.progettopwm.prenotazioni

import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.databinding.FragmentPrenotazioneRistoranteBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import com.matteopaterno.progettopwm.ristoranti.RistorantiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PrenotazioneRistorantiFragment : Fragment() {
    private lateinit var binding: FragmentPrenotazioneRistoranteBinding
    private lateinit var calendar: Calendar
    private lateinit var loginPreferences : SharedPreferences
    private lateinit var loginPrefsEditor : SharedPreferences.Editor
    private lateinit var dataPrenotazione : String
    private var guests = 0
    private var orarioPrenotazione : String = ""

    private var ristorante: RistorantiData? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        loginPrefsEditor = loginPreferences.edit()


        val idUtente = loginPreferences.getString("id", "")?.toInt()

        var checkInFocused: Boolean
        binding = FragmentPrenotazioneRistoranteBinding.inflate(layoutInflater)
        calendar = Calendar.getInstance()

        binding.editTextDataPrenotazione.isFocusable = false
        binding.editTextDataPrenotazione.isClickable = true
        binding.editTextDataPrenotazione.setOnClickListener{
            checkInFocused = true
            showDatePicker(checkInFocused)
        }



        val spinner = binding.spinnernumero
        val intArray: Array<Int?> = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item,intArray)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter
        spinner.prompt = "Numero di ospiti"

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                guests = parent?.getItemAtPosition(position).toString().toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }


       binding.timePicker.setOnTimeChangedListener { _, hourOfDay, minute ->
           val orarioSelezionato = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute)
           orarioPrenotazione = orarioSelezionato
       }


        binding.buttonSubmit.setOnClickListener{
            effetuaPrenotazione(idUtente, ristorante?.id, dataPrenotazione, guests, orarioPrenotazione)
        }



        binding.cartButton.setOnClickListener {
            val randId = (1..50000).random()
            val tipoPrenotazione = TipoPrenotazione.RISTORANTE

            val prenotazione = PrenotazioneData(
                id = randId,
                idRistorante = ristorante?.id,
                nome = ristorante?.nome,
                posizione = ristorante?.posizione,
                citta = ristorante?.citta,
                tipoPrenotazione = tipoPrenotazione,
                restaurantGuests = guests,
                orarioPrenotazione = orarioPrenotazione,
                dataPrenotazione = dataPrenotazione
            )

                ManagerCarrello.aggiungiAlCarrello(prenotazione)
                Toast.makeText(context, "Prenotazione aggiunta al carrello", Toast.LENGTH_SHORT).show()
            }

        return binding.root
    }

    private fun showDatePicker(checkInFocused: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener{_ : DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            if (checkInFocused){
                updateDataPrenotazione()
            }
        }

        DatePickerDialog(
            requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    private fun updateDataPrenotazione() {
        val formato = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formato, Locale.US)
        binding.editTextDataPrenotazione.setText(dateFormat.format(calendar.time))
        val date = Date()
        dataPrenotazione = dateFormat.format(date)
    }
    fun setRistorante (ristorante: RistorantiData){
        this.ristorante = ristorante
    }

    companion object{
        fun newInstance(ristorante: RistorantiData): PrenotazioneRistorantiFragment {
            val fragment = PrenotazioneRistorantiFragment()
            fragment.setRistorante(ristorante)
            return fragment
        }
    }
    fun effetuaPrenotazione(
        idUtente: Int?,
        idRistorante: Int?,
        dataPrenotazione: String,
        guests: Int,
        orarioPrenotazione: String, ){
       val query = "INSERT INTO webmobile.restaurant_reservation (user_id, ristorante_id, guests, reservation_date, reservation_time) VALUES ('${idUtente}', '${idRistorante}','${guests}', '${dataPrenotazione}', '${orarioPrenotazione}')"

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
}