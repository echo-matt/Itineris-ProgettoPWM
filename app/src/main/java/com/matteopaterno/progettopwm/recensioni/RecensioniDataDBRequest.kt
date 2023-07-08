package com.matteopaterno.progettopwm.recensioni

import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecensioniHotelDataDBRequest {


    fun createRecensioniList(
        callback: (List<RecensioniHotelData>) -> Unit
    ){
        val data = ArrayList<RecensioniHotelData>()
        val query = """
        SELECT r.*, u.nome
        FROM recensioni_hotel r
        INNER JOIN users u ON r.user_id = u.id
    """.trimIndent()

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful){
                        val jsonResponse =
                            response.body() //Prendo il response.body e lo salvo in una variabile
                        val queryset =
                            jsonResponse?.getAsJsonArray("queryset") //Prendo il jsonArray di risposta

                        if (queryset != null){
                            for (j in 0 until queryset.size()){
                                val jsonObject = queryset[j].asJsonObject

                                val testo = jsonObject?.get("testo")?.asString
                                val userId = jsonObject?.get("user_id")?.asInt
                                val hotelId = jsonObject?.get("hotel_id")?.asInt
                                val rating = jsonObject?.get("rating")?.asFloat
                                val nomeUtente = jsonObject?.get("nome")?.asString

                                data.add(
                                    RecensioniHotelData(
                                        testo,
                                        userId,
                                        nomeUtente,
                                        hotelId,
                                        rating
                                    )
                                )
                            }
                        }
                        RecensioniDataListHolder.recensioniHotelDataList.clear()
                        RecensioniDataListHolder.recensioniHotelDataList.addAll(data)
                        callback(data)
                        callback(RecensioniDataListHolder.recensioniHotelDataList)
                    }else{
                        callback(emptyList())
                    }

                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                    callback(emptyList())
                }
            }
        )
    }
}