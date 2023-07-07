package com.matteopaterno.progettopwm.prenotazioni

data class HotelPrenotazioneData(

    val id: Int?,
    val userId: Int?,
    val hotelNome: String?,
    val checkInDate: String,
    val checkOutDate: String,
    val guests: Int,
    var paymentStatus: String
)

data class RistorantePrenotazioneData(
    val id: Int?,
    val userId: Int?,
    val ristoranteNome: String?,
    val data: String,
    val guests: Int
)
