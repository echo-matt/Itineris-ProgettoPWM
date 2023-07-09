package com.matteopaterno.progettopwm.ristoranti

import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.hotel.HotelData
import com.matteopaterno.progettopwm.hotel.HotelDataListHolder
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RistorantiDataDBRequest {

    fun createRistorantiList(callback: (List<RistorantiData>) -> Unit){
        val data = ArrayList<RistorantiData>()
        val query = "select * from restaurants;"

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
                                val ristoranteId = jsonObject?.get("id")?.asInt
                                val ristoranteName = jsonObject?.get("nome")?.asString
                                val ristoranteAddress = jsonObject?.get("posizione")?.asString
                                val ristoranteRating = jsonObject?.get("rating")?.asFloat
                                val ristoranteCitta = jsonObject?.get("citta")?.asString
                                //Setto i dati del singolo ristorante con quelli appena recuperati dal server
                                data.add(
                                    RistorantiData(
                                        ristoranteId,
                                        ristoranteName,
                                        ristoranteAddress,
                                        ristoranteRating,
                                        ristoranteCitta
                                    )
                                )
                            }
                        }
                    }
                    RistorantiDataListHolder.RistorantiDataList.clear()
                    RistorantiDataListHolder.RistorantiDataList.addAll(data)

                    RistorantiDataListHolder.filteredRistorantiDataList.clear()
                    RistorantiDataListHolder.filteredRistorantiDataList.addAll(data)
                    callback(RistorantiDataListHolder.RistorantiDataList)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                    callback(ArrayList())
                }
            })
    }

}