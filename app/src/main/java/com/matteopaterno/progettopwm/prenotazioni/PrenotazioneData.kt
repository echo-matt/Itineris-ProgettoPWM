package com.matteopaterno.progettopwm.prenotazioni

data class PrenotazioneData(
    val id: Int?,
    val nome: String?,
    val posizione: String?,
    val checkInDate: String?,
    val checkOutDate: String?,
    val citta: String?,
    val tipoPrenotazione: ReservationType
)

enum class ReservationType {
    HOTEL, RISTORANTE
}
