package com.matteopaterno.progettopwm

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView

class RistorantiAdapter(private val ristorantiList: ArrayList<Ristoranti>) : RecyclerView.Adapter<RistorantiAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.lista_ristoranti, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return ristorantiList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = ristorantiList[position]
        holder.titleImage.setImageResource(currentItem.image)
        holder.tvHeading.text = currentItem.text
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val titleImage : ShapeableImageView = itemView.findViewById(R.id.title_image)
        val tvHeading : TextView = itemView.findViewById(R.id.tvHeading)

    }

}