package com.matteopaterno.progettopwm.prenotazioni

object ManagerCarrello {
    private val carrello: Carrello = Carrello()

    fun aggiungiAlCarrello(prenotazione: PrenotazioneData){
        carrello.aggiungiPrenotazione(prenotazione)
    }

    fun rimuoviDalCarrello(prenotazione: PrenotazioneData){
        carrello.rimuoviPrenotazione(prenotazione)
    }

    fun svuotaCarrello(){
        carrello.svuotaCarrello()
    }

    fun getPrenotazioni(): List<PrenotazioneData>{
        return carrello.getPrenotazioni()
    }

}