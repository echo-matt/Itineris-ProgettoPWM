package com.matteopaterno.progettopwm.prenotazioni

class Carrello {

    private val prenotazioni : MutableList<PrenotazioneData> = mutableListOf()

    fun aggiungiPrenotazione(prenotazione: PrenotazioneData){
        prenotazioni.add(prenotazione)
    }
    fun rimuoviPrenotazione(prenotazione: PrenotazioneData){
        prenotazioni.remove(prenotazione)
    }

    fun getPrenotazioni(): MutableList<PrenotazioneData> {
        return prenotazioni
    }


    fun svuotaCarrello(){
        prenotazioni.clear()
    }
}