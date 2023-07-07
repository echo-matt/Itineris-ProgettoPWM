package com.matteopaterno.progettopwm.prenotazioni

class Carrello {

    private val prenotazioniHotel: MutableList<HotelPrenotazioneData> = mutableListOf()
    private val prenotazioniRistoranti: MutableList<RistorantePrenotazioneData> = mutableListOf()

    fun aggiungiPrenotazioneHotel(prenotazione: HotelPrenotazioneData){
        prenotazioniHotel.add(prenotazione)
    }

    fun aggiungiPrenotazioneRistorante(prenotazione: RistorantePrenotazioneData){
        prenotazioniRistoranti.add(prenotazione)
    }

    fun rimuoviPrenotazioneHotel(prenotazione: HotelPrenotazioneData){
        prenotazioniHotel.remove(prenotazione)
    }

    fun rimuoviPrenotazioneRistorante(prenotazione: RistorantePrenotazioneData){
        prenotazioniRistoranti.remove(prenotazione)
    }

    fun getPrenotazioneHotel(): MutableList<HotelPrenotazioneData> {
        return prenotazioniHotel
    }

    fun getPrenotazioneRistorante(): MutableList<RistorantePrenotazioneData> {
        return prenotazioniRistoranti
    }

    fun svuotaCarrello(){
        prenotazioniHotel.clear()
        prenotazioniRistoranti.clear()
    }
}