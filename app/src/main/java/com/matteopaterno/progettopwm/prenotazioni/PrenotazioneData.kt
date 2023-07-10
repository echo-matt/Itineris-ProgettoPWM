package com.matteopaterno.progettopwm.prenotazioni

data class PrenotazioneData(
    val id: Int?,
    val nome: String?,
    val posizione: String?,
    val checkInDate: String?= null,
    val checkOutDate: String? = null,
    val citta: String?,
    val orarioPrenotazione: String? = null,
    val tipoPrenotazione: TipoPrenotazione,
    val costoPrenotazione: Int? = null
)

enum class TipoPrenotazione {
    HOTEL, RISTORANTE
}
