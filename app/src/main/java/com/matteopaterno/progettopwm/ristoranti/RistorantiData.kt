package com.matteopaterno.progettopwm.ristoranti
import com.google.gson.annotations.SerializedName

data class RistorantiData(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("nome")
    var nome: String?,

    @SerializedName("posizione")
    var posizione: String?,

    @SerializedName("rating")
    var rating: Float?,

    @SerializedName("citta")
    var citta: String?
)
