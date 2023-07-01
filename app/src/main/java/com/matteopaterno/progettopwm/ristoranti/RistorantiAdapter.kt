package com.matteopaterno.progettopwm.ristoranti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.databinding.RistorantiCardViewBinding

class RistorantiAdapter(private val ristorantiDataList: ArrayList<RistorantiData>) : RecyclerView.Adapter<RistorantiAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(ristorante: RistorantiData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class ViewHolder(binding: RistorantiCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val ratingBar = binding.ratingBar
        val posizione = binding.textPosizione
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RistorantiCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ristorantiDataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = ristorantiDataList[position]
        holder.imageView.setImageResource(currentItem.image)
        holder.nome.text = currentItem.nome
        holder.ratingBar.rating = currentItem.rating
        holder.posizione.text = currentItem.posizione

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }
}
