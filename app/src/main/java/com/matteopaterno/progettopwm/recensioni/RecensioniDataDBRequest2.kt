package com.matteopaterno.progettopwm.recensioni

import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecensioniHotelDataDBRequest2(forHotel: Boolean) {
    var forHotel = forHotel

    fun createRecensioniList(hotelId: Int, ristoranteId: Int, callback: (List<Any>) -> Unit) {

        if (forHotel) {
            createRecensioniHotelList(hotelId) { hotelData ->
                callback(hotelData)
            }
        } else {
            createRecensioniRistorantiList(ristoranteId) { RistorantiData ->
                callback(RistorantiData)
            }
        }
    }

    fun createRecensioniHotelList(hotelId: Int, callback: (List<RecensioniHotelData>) -> Unit) {
        val data = ArrayList<RecensioniHotelData>()
        val query = """
            SELECT r.*, u.nome
            FROM recensioni_hotel r
            INNER JOIN users u ON r.user_id = u.id
            WHERE r.hotel_id = $hotelId
        """.trimIndent()

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse =
                            response.body() //Prendo il response.body e lo salvo in una variabile
                        val queryset =
                            jsonResponse?.getAsJsonArray("queryset") //Prendo il jsonArray di risposta

                        if (queryset != null) {
                            for (j in 0 until queryset.size()) {
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
                    } else {
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

    fun createRecensioniRistorantiList(
        ristoranteId: Int,
        callback: (List<RecensioniRistorantiData>) -> Unit
    ) {
        val data = ArrayList<RecensioniRistorantiData>()
        val query = """
        SELECT r.*, u.nome
        FROM recensioni_ristoranti r
        INNER JOIN users u ON r.user_id = u.id
        WHERE r.ristorante_id = $ristoranteId
    """.trimIndent()

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse =
                            response.body() //Prendo il response.body e lo salvo in una variabile
                        val queryset =
                            jsonResponse?.getAsJsonArray("queryset") //Prendo il jsonArray di risposta

                        if (queryset != null) {
                            for (j in 0 until queryset.size()) {
                                val jsonObject = queryset[j].asJsonObject

                                val testo = jsonObject?.get("testo")?.asString
                                val userId = jsonObject?.get("user_id")?.asInt
                                val ristoranteId = jsonObject?.get("ristorante_id")?.asInt
                                val rating = jsonObject?.get("rating")?.asFloat
                                val nomeUtente = jsonObject?.get("nome")?.asString

                                data.add(
                                    RecensioniRistorantiData(
                                        testo,
                                        userId,
                                        nomeUtente,
                                        ristoranteId,
                                        rating
                                    )
                                )
                            }
                        }
                        RecensioniDataListHolder.recensioniRistorantiDataList.clear()
                        RecensioniDataListHolder.recensioniRistorantiDataList.addAll(data)
                        callback(data)
                        callback(RecensioniDataListHolder.recensioniRistorantiDataList)
                    } else {
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
