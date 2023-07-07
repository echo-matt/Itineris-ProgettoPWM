package com.matteopaterno.progettopwm.hotel

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matteopaterno.progettopwm.databinding.HotelCardViewDesignBinding
import com.matteopaterno.progettopwm.ristoranti.RistorantiAdapter

class HotelAdapter(private val HotelLista: List<HotelData>) : RecyclerView.Adapter<HotelAdapter.ViewHolder>(){

    private var onItemClickListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(hotel: HotelData)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }

    inner class ViewHolder(binding: HotelCardViewDesignBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val ratingBar = binding.ratingBar
        val posizione = binding.textPosizione

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    onItemClickListener?.onItemClick(HotelLista[position])
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = HotelCardViewDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return HotelLista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = HotelLista[position]

        //Settato valori default se non presenti in DB
        holder.imageView.setImageResource(currentItem.id?: 0)
        holder.nome.text = currentItem.nome?: ""
        holder.ratingBar.rating = currentItem.rating?: 0.0f
        holder.posizione.text = currentItem.posizione?: ""

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }

    fun filterRecyclerView(filterText: String){
        val filteredList = if (filterText.isNotBlank()){
            HotelDataListHolder.hotelDataList.filter { item ->
                item.citta!!.contains(filterText, ignoreCase = true)
            }.toMutableList()
        }else{
                HotelDataListHolder.hotelDataList.toMutableList()
        }

        HotelDataListHolder.filteredHotelDataList.clear()
        HotelDataListHolder.filteredHotelDataList.addAll(filteredList)
        notifyDataSetChanged()
    }
}