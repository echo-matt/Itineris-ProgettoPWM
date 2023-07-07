package com.matteopaterno.progettopwm.recensioni

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.matteopaterno.progettopwm.databinding.RecensioneDesignBinding

class RecensioniAdapter(private val reviews: List<RecensioniHotelData>): RecyclerView.Adapter<RecensioniAdapter.ViewHolder>(){

    inner class ViewHolder(binding: RecensioneDesignBinding) : RecyclerView.ViewHolder(binding.root){

        val textViewUserName: TextView = binding.textViewUserName
        val ratingBar: RatingBar = binding.ratingBar
        val textViewComment: TextView = binding.textViewComment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RecensioneDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = reviews[position]

        holder.textViewUserName.text = currentItem.user_id.toString()
        holder.ratingBar.rating = currentItem.rating!!
        holder.textViewComment.text = currentItem.testo
    }
}