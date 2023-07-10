package com.matteopaterno.progettopwm.prenotazioni

data class PrenotazioneData(
    val id: Int?= null,
    val nome: String?= null,
    val posizione: String? = null,
    val checkInDate: String? = null,
    val checkOutDate: String? = null,
    val citta: String? = null,
    val orarioPrenotazione: String? = null,
    val tipoPrenotazione: TipoPrenotazione? =null,
    val costoPrenotazione: Double? = null,
    val idHotel: Int? = null,
    val idRistorante: Int? = null,
    val hotelGuests: Int? = null,
    val restaurantGuests: Int? = null,
    val dataPrenotazione: String? = null
)

enum class TipoPrenotazione {
    HOTEL, RISTORANTE
}
