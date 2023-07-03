package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.databinding.AttrazioniCardViewBinding

class AttrazioniAdapter(private val attrazioniDataList: ArrayList<AttrazioniData>) : RecyclerView.Adapter<AttrazioniAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(attrazione: AttrazioniData)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class ViewHolder(binding: AttrazioniCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val ratingBar = binding.ratingBar
        val posizione = binding.textPosizione
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AttrazioniCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return attrazioniDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = attrazioniDataList[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.nome.text = currentItem.nome
        holder.ratingBar.rating = currentItem.rating
        holder.posizione.text = currentItem.posizione

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }
}
