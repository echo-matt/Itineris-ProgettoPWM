package com.matteopaterno.progettopwm.ristoranti
import com.google.gson.annotations.SerializedName

data class RistorantiData(

    var image: Int,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("posizione")
    var posizione: String,

    @SerializedName("rating")
    var rating: Float
)
