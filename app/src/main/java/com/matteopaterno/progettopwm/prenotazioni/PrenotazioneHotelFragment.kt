package com.matteopaterno.progettopwm.prenotazioni

import android.app.AlertDialog
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
import com.matteopaterno.progettopwm.databinding.DialogPaymentBinding
import com.matteopaterno.progettopwm.databinding.FragmentPrenotazioneHotelBinding
import com.matteopaterno.progettopwm.hotel.HotelData
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class PrenotazioneHotelFragment : Fragment() {
    private lateinit var binding: FragmentPrenotazioneHotelBinding
    private lateinit var bindingPagamento: DialogPaymentBinding
    private lateinit var calendar: Calendar
    private lateinit var loginPreferences : SharedPreferences
    private lateinit var loginPrefsEditor : SharedPreferences.Editor
    private lateinit var checkInDateString : String
    private lateinit var checkOutDateString : String
    private var guests: Int = 1
    private var hotel: HotelData? = null
    private var costo: Double = hotel?.costo?: 0.0


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
        binding = FragmentPrenotazioneHotelBinding.inflate(layoutInflater)


        calendar = Calendar.getInstance()

        binding.editTextCheckIn.isFocusable = false
        binding.editTextCheckIn.isClickable = true
        binding.editTextCheckIn.setOnClickListener{
            checkInFocused = true
            showDatePicker(checkInFocused)
        }

        binding.editTextCheckOut.isFocusable = false
        binding.editTextCheckOut.isClickable = true
        binding.editTextCheckOut.setOnClickListener{
            checkInFocused = false
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
                guests = 1
            }
        }



        binding.buttonSubmit.setOnClickListener{
            val dialogBuilder = AlertDialog.Builder(requireContext())
            val binding2 = DialogPaymentBinding.inflate(layoutInflater)
            val dialogView = binding2.root
            dialogBuilder.setView(dialogView)

            val checkInDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkInDateString)
            val checkOutDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkOutDateString)

            val numberOfDay = calculateNumberOfDays(checkInDate!!, checkOutDate!!)

            costo = (hotel?.costo!! * numberOfDay.toDouble()) + (20*guests)
            val costoString = costo.toString()
            binding2.costoTotale.text = "Costo totale della prenotazione: $costoString €"

            val amountText = binding2.editTextAmount

            dialogBuilder.setTitle("Pagamento")
            dialogBuilder.setPositiveButton("Paga"){_, _ ->
                val amount = amountText.text.toString()
                if (amount.isNotEmpty()){
                    val double = amount.toDouble()
                    if (double == costo!!){
                        Toast.makeText(context, "Pagamento effettuato, prenotazione registrata", Toast.LENGTH_SHORT).show()
                        effetuaPrenotazione(idUtente, hotel?.id, checkInDateString, checkOutDateString, guests)
                    }else if (double > costo!!){
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

        binding.cartButton.setOnClickListener {
            val randId = (1..50000).random()
            val tipoPrenotazione = TipoPrenotazione.HOTEL
            val checkInDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkInDateString)
            val checkOutDate = SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkOutDateString)

            val numberOfDay = calculateNumberOfDays(checkInDate!!, checkOutDate!!)

            costo = (hotel?.costo!! * numberOfDay.toDouble()) + (20*guests)

            val prenotazione = PrenotazioneData(
                id = randId,
                nome = hotel?.nome,
                posizione = hotel?.posizione,
                checkInDate = checkInDateString,
                checkOutDate = checkOutDateString,
                citta = hotel?.citta,
                tipoPrenotazione = tipoPrenotazione,
                costoPrenotazione = costo,
                idHotel = hotel?.id,
                hotelGuests = guests,
                dataPrenotazione = null
            )

            ManagerCarrello.aggiungiAlCarrello(prenotazione)
            Toast.makeText(context, "Prenotazione aggiunta al carrello", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun calculateNumberOfDays(checkInDate: Date, checkOutDate: Date): Long {
        val diff = checkOutDate.time - checkInDate.time
        return diff / (24 * 60 * 60 * 1000)
    }

    private fun showDatePicker(checkInFocused: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener{_ : DatePicker, year: Int, month: Int, day: Int ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            if (checkInFocused){
                updateCheckInDate()
            }else{
                updateCheckOutDate()
            }

        }

        DatePickerDialog(
            requireContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)
        ).show()

    }

    private fun updateCheckInDate() {
        val formato = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formato, Locale.US)
        val selectedDate = calendar.time
        binding.editTextCheckIn.setText(dateFormat.format(selectedDate))
        checkInDateString = dateFormat.format(selectedDate)
    }

    private fun updateCheckOutDate() {
        val formato = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(formato, Locale.US)
        val selectedDate = calendar.time
        binding.editTextCheckOut.setText(dateFormat.format(selectedDate))
        checkOutDateString = dateFormat.format(selectedDate)
    }
    fun setHotel(hotel: HotelData){
        this.hotel = hotel
    }

    companion object{
        fun newInstance(hotel: HotelData): PrenotazioneHotelFragment {
            val fragment = PrenotazioneHotelFragment()
            fragment.setHotel(hotel)
            return fragment
        }
    }
    fun effetuaPrenotazione(idUtente: Int?, idHotel: Int?, checkInDate: String, checkOutDate: String, guests: Int, ){
       val query = "INSERT INTO webmobile.hotel_reservations (user_id, hotel_id, check_in_date, check_out_date, guests, payment_status) VALUES ('${idUtente}', '${idHotel}', '${checkInDate.toString()}', '${checkOutDate.toString()}', '${guests}', 'Pagato')"

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