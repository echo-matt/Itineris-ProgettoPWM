package com.matteopaterno.progettopwm.hotel

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentHotelBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelFragment : Fragment() {
    private lateinit var binding : FragmentHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.hotelRecyclerView.adapter = createHotelList()
    }

    public fun createHotelList() : HotelAdapter{
        val data = ArrayList<HotelData>()
        val query = "select * from hotels;"

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse =
                            response.body() //Prendo il response.body e lo salvo in una variabile
                        val queryset =
                            jsonResponse?.getAsJsonArray("queryset") //Prendo il jsonArray di risposta

                        if (queryset != null) { //Se il body della risposta esiste
                            for (j in 0 until queryset.size()) { //Itero tra gli elementi del JsonArray
                                val jsonObject =
                                    queryset[j].asJsonObject //Salvo l'elemento j-esimo dell'array in un JsonObject

                                //Accedo ai singoli campi del JSONObject
                                val hotelName = jsonObject?.get("nome")?.asString
                                val hotelAddress = jsonObject?.get("posizione")?.asString
                                val hotelRating = jsonObject?.get("rating")?.asFloat
                                val hotelCitta = jsonObject?.get("citta")?.asString
                                //Setto i dati del singolo hotel con quelli appena recuperati dal server
                                data.add(
                                    HotelData(
                                        R.drawable.photo_1506905925346_21bda4d32df4,
                                        hotelName,
                                        hotelAddress,
                                        hotelRating,
                                        hotelCitta
                                    )
                                )
                                HotelDataListHolder.hotelDataList.addAll(data)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(activity, "Error failure", Toast.LENGTH_SHORT).show()
                    t.printStackTrace()
                }
            })
        val adapter = HotelAdapter(data)
        return adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHotelBinding.inflate(layoutInflater)
        super.onCreateView(inflater, container, savedInstanceState)
        binding.hotelRecyclerView.layoutManager = LinearLayoutManager(context)


        /*
        val cityEditText = binding.cityEditText
        cityEditText.setOnEditorActionListener{_, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE){
                applyFilters()
                true
            }else{
                false
            }
        }
         */


        return binding.root
    }

    /*
    private fun applyFilters() {
        val city = binding.cityEditText.text.toString().trim()
        val filteredData = ArrayList<HotelData>()
        for (hotel in filteredData){
            if ((city.isEmpty() || hotel.citta.equals(city, ignoreCase = true))){
                filteredData.add(hotel)
            }
        }

        val adapter = HotelAdapter(filteredData)
        binding.hotelRecyclerView.adapter = adapter
    }

     */
}