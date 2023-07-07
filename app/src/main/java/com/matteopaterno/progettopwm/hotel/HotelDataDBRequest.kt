package com.matteopaterno.progettopwm.hotel

import android.widget.Toast
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelDataDBRequest {
    fun createHotelList(callback: (List<HotelData>) -> Unit){
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
                                val jsonObject = queryset[j].asJsonObject //Salvo l'elemento j-esimo dell'array in un JsonObject

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
                            }
                        }
                    }
                    HotelDataListHolder.hotelDataList.clear()
                    HotelDataListHolder.hotelDataList.addAll(data)

                    HotelDataListHolder.filteredHotelDataList.clear()
                    HotelDataListHolder.filteredHotelDataList.addAll(data)
                    callback(HotelDataListHolder.hotelDataList)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                    callback(ArrayList())
                }
            })
    }
}