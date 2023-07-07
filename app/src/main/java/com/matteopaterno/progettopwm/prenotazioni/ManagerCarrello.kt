package com.matteopaterno.progettopwm.prenotazioni

object ManagerCarrello {
    private val carrello: Carrello = Carrello()

    fun aggiungiAlCarrello(prenotazione: Any){
        when (prenotazione){
            is HotelPrenotazioneData -> carrello.aggiungiPrenotazioneHotel(prenotazione)
            is RistorantePrenotazioneData -> carrello.aggiungiPrenotazioneRistorante(prenotazione)
        }
    }

    fun rimuoviDalCarrello(prenotazione: Any){
        when (prenotazione){
            is HotelPrenotazioneData -> carrello.rimuoviPrenotazioneHotel(prenotazione)
            is RistorantePrenotazioneData -> carrello.aggiungiPrenotazioneRistorante(prenotazione)
        }
    }

    fun svuotaCarrello(){
        carrello.svuotaCarrello()
    }

    fun getPrenotazioniHotel(): List<HotelPrenotazioneData>{
        return carrello.getPrenotazioneHotel()
    }

    fun getPrenotazioniRistorante(): List<RistorantePrenotazioneData>{
        return carrello.getPrenotazioneRistorante()
    }
}