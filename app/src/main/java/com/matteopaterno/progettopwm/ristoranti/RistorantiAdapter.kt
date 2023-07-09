package com.matteopaterno.progettopwm.ristoranti

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.databinding.RistorantiCardViewBinding

class RistorantiAdapter(private val ristorantiDataList: List<RistorantiData>) : RecyclerView.Adapter<RistorantiAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(ristorante: RistorantiData)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(binding: RistorantiCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val ratingBar = binding.ratingBar
        val posizione = binding.textPosizione

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    val ristorante = ristorantiDataList[position]
                    onItemClickListener?.onItemClick(ristorantiDataList[position])
                }
            }
        }
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

        holder.nome.text = currentItem.nome?: ""
        holder.ratingBar.rating = currentItem.rating?: 0.0f
        holder.posizione.text = currentItem.posizione?: ""

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }
}
