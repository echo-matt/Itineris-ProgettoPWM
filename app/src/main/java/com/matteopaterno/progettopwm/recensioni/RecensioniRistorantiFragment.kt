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
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import com.matteopaterno.progettopwm.ristoranti.RistorantiData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecensioniRistorantiFragment : Fragment() {
    private lateinit var binding : FragmentRecensioniBinding
    private lateinit var testo: String
    private lateinit var loginPreferences: SharedPreferences
    private var ristorante: RistorantiData? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var recensioniRistorantiAdapter: RecensioniAdapter<RecensioniRistorantiData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecensioniBinding.inflate(layoutInflater)
        loginPreferences = requireActivity().getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)

        recyclerView = binding.recyclerViewReviews
        recyclerView.layoutManager = LinearLayoutManager(context)
        recensioniRistorantiAdapter = RecensioniAdapter(RecensioniDataListHolder.recensioniRistorantiDataList)
        recyclerView.adapter = recensioniRistorantiAdapter


        binding.sendButton.setOnClickListener {
            testo = binding.editTextText.text.toString()
            val idUtente = loginPreferences.getString("id", "")?.toInt()
            val nomeUtente = loginPreferences.getString("nome", "")
            val idRistorante = ristorante?.id!!
            val rating = binding.ratingBar.rating
            inviaRecensioneRistorante(idRistorante, idUtente, testo, rating) {success ->
                if (success){
                    val newReview = RecensioniRistorantiData(testo, idUtente, nomeUtente,  idRistorante, rating)
                    RecensioniDataListHolder.recensioniRistorantiDataList.add(newReview)
                    RecensioniAdapter(RecensioniDataListHolder.recensioniRistorantiDataList).notifyItemInserted(RecensioniDataListHolder.recensioniRistorantiDataList.size - 1)
                    recyclerView.scrollToPosition(RecensioniDataListHolder.recensioniRistorantiDataList.size - 1)
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
        val query = "INSERT INTO webmobile.recensioni_ristoranti (ristorante_id, user_id, testo, rating) VALUES ('${idHotel}', '${idUtente}', '${testo}', '${rating}')"

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

    fun setHotel(ristorante: RistorantiData){
        this.ristorante = ristorante
    }

    companion object{
        fun newInstance(ristorante: RistorantiData): RecensioniRistorantiFragment{
            val fragment = RecensioniRistorantiFragment()
            fragment.setHotel(ristorante)
            return fragment
        }
    }

}