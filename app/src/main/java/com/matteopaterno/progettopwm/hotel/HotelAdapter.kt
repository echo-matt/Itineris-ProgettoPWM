package com.matteopaterno.progettopwm.hotel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.databinding.HotelCardViewDesignBinding

class HotelAdapter(private val HotelLista: List<HotelData>) : RecyclerView.Adapter<HotelAdapter.ViewHolder>(){

    class ViewHolder(binding: HotelCardViewDesignBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val ratingBar = binding.ratingBar
        val posizione = binding.textPosizione
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HotelCardViewDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return HotelLista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val datiHotel = HotelLista[position]

        holder.imageView.setImageResource(datiHotel.image)
        holder.nome.text = datiHotel.nome
        holder.ratingBar
        holder.posizione.text = datiHotel.posizione
    }
}