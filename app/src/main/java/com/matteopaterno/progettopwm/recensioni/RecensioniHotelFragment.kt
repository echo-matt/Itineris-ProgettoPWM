package com.matteopaterno.progettopwm.recensioni

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.databinding.FragmentRecensioniBinding
import com.matteopaterno.progettopwm.hotel.HotelData
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecensioniHotelFragment : Fragment() {
    private lateinit var binding : FragmentRecensioniBinding
    private lateinit var testo: String
    private lateinit var loginPreferences: SharedPreferences
    private var hotel: HotelData? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recensioniHotelAdapter: RecensioniAdapter<RecensioniHotelData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecensioniBinding.inflate(layoutInflater)
        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        recyclerView = binding.recyclerViewReviews
        recyclerView.layoutManager = LinearLayoutManager(context)
        recensioniHotelAdapter = RecensioniAdapter(RecensioniDataListHolder.recensioniHotelDataList)
        recyclerView.adapter = recensioniHotelAdapter


        binding.sendButton.setOnClickListener {
            testo = binding.editTextText.text.toString()
            val idUtente = loginPreferences.getString("id", "")?.toInt()
            val nomeUtente = loginPreferences.getString("nome", "")
            val idHotel = hotel?.id!!
            val rating = binding.ratingBar.rating
            inviaRecensioneHotel(idHotel, idUtente, testo, rating) {success ->
                if (success){
                    val newReview = RecensioniHotelData(testo, idUtente, nomeUtente,  idHotel, rating)
                    RecensioniDataListHolder.recensioniHotelDataList.add(newReview)
                    RecensioniAdapter(RecensioniDataListHolder.recensioniHotelDataList).notifyItemInserted(RecensioniDataListHolder.recensioniHotelDataList.size - 1)
                    recyclerView.scrollToPosition(RecensioniDataListHolder.recensioniHotelDataList.size - 1)
                }else{
                    Toast.makeText(activity, "Errore nell'invio della recensione", Toast.LENGTH_SHORT).show()
                }
            }

            binding.editTextText.text.clear()
            binding.ratingBar.rating = 0f
        }

        return binding.root
    }

    fun inviaRecensioneHotel(idHotel: Int?, idUtente: Int?, testo: String?, rating: Float, callback: (Boolean) -> Unit) {
        val query = "INSERT INTO webmobile.recensioni_hotel (hotel_id, user_id, testo, rating) VALUES ('${idHotel}', '${idUtente}', '${testo}', '${rating}')"

        ClientNetwork.retrofit.insert(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        Toast.makeText(activity, "Recensione inviata", Toast.LENGTH_SHORT).show()
                        callback(true)
                    }else{
                        Toast.makeText(activity, "Errore nella richiesta", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                    callback(false)
                }

            }
        )
    }

    fun inviaRecensioneRistorante(idRistorante: Int?, idUtente: Int?, testo: String?, rating: Float, callback: (Boolean) -> Unit) {
        val query = "INSERT INTO webmobile.recensioni_ristoranti (ristorante_id, user_id, testo, rating) VALUES ('${idRistorante}', '${idUtente}', '${testo}', '${rating}')"

        ClientNetwork.retrofit.insert(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        Toast.makeText(activity, "Recensione inviata", Toast.LENGTH_SHORT).show()
                        callback(true)
                    }else{
                        Toast.makeText(activity, "Errore nella richiesta", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Errore nella richiesta, riprova", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                    callback(false)
                }

            }
        )
    }

    fun setHotel(hotel: HotelData){
        this.hotel = hotel
    }

    companion object{
        fun newInstance(hotel: HotelData): RecensioniHotelFragment{
            val fragment = RecensioniHotelFragment()
            fragment.setHotel(hotel)
            return fragment
        }
    }

}