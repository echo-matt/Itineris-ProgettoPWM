package com.matteopaterno.progettopwm.recensioni

data class RecensioniHotelData(
    val testo: String?,
    val user_id : Int?,
    val nomeUtente: String?,
    val hotel_id : Int?,
    val rating: Float?
)

data class RecensioniRistorantiData(
    val testo: String?,
    val user_id : Int?,
    val nomeUtente: String?,
    val ristorante_id : Int?,
    val rating: Float?
)
