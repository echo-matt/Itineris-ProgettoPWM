package com.matteopaterno.progettopwm.hotel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.HotelCardViewDesignBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

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
                    val hotel = HotelLista[position]
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

        getHotelImage(currentItem.id) { imagePath ->
            setHotelImage(imagePath, holder.imageView)
            val overlay: Drawable? = ContextCompat.getDrawable(holder.imageView.context, R.drawable.shadow_bg)
            holder.imageView.foreground = overlay
        }

        /*
        getHotelImage(currentItem.id) { imagePath ->
            setHotelImage(imagePath, holder.imageView)
            val overlay: Drawable? = ContextCompat.getDrawable(holder.imageView.context, R.drawable.shadow_bg)
            holder.imageView.foreground = overlay
        }

         */

        //Settato valori default se non presenti in DB

        holder.nome.text = currentItem.nome?: ""
        holder.ratingBar.rating = currentItem.rating?: 0.0f
        holder.posizione.text = currentItem.posizione?: ""

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }


    }

    private fun getHotelImage(hotelId: Int?, callback: (String) -> Unit) {
        val query = "SELECT img FROM hotels WHERE id = $hotelId"

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse = response.body()
                        val jsonArray = jsonResponse?.getAsJsonArray("queryset")
                        if (jsonArray != null && jsonArray.size() > 0) {
                            val jsonObject = jsonArray.get(0).asJsonObject
                            if (jsonObject != null) {
                                val imagePath = jsonObject.get("img").asString
                                callback(imagePath)
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

    private fun setHotelImage(url: String?, imageView: ImageView) {
        ClientNetwork.retrofit.image(url!!).enqueue(
            object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        val image: Bitmap? = BitmapFactory.decodeStream(
                            ByteArrayInputStream(
                                response.body()!!.bytes()
                            )
                        )

                        imageView.setImageBitmap(image)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
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