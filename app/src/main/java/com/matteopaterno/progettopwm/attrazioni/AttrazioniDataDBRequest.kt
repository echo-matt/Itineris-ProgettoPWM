package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AttrazioniDataDBRequest {

    fun createAttrazioniList(callback: (List<AttrazioniData>) -> Unit){
        val data = ArrayList<AttrazioniData>()
        val query = "select * from attrazioni;"

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
                                val attrazioneId = jsonObject?.get("id")?.asInt
                                val attrazioneName = jsonObject?.get("nome")?.asString
                                val attrazioneDesc = jsonObject?.get("descrizione")?.asString
                                val attrazioneCitta = jsonObject?.get("citta")?.asString
                                //Setto i dati del singolo attrazione con quelli appena recuperati dal server
                                data.add(
                                    AttrazioniData(
                                        attrazioneId,
                                        attrazioneName,
                                        attrazioneDesc,
                                        attrazioneCitta
                                    )
                                )
                            }
                        }
                    }
                    AttrazioniDataListHolder.AttrazioniDataList.clear()
                    AttrazioniDataListHolder.AttrazioniDataList.addAll(data)

                    AttrazioniDataListHolder.filteredAttrazioniDataList.clear()
                    AttrazioniDataListHolder.filteredAttrazioniDataList.addAll(data)
                    callback(AttrazioniDataListHolder.AttrazioniDataList)
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                    callback(ArrayList())
                }
            })
    }
}