package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.AttrazioniCardViewBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class AttrazioniAdapter(private val attrazioniDataList: List<AttrazioniData>) : RecyclerView.Adapter<AttrazioniAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(attrazione: AttrazioniData)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    inner class ViewHolder(binding: AttrazioniCardViewBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView = binding.imageView
        val nome = binding.textNome
        val citta = binding.textPosizione

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                    onItemClickListener?.onItemClick(attrazioniDataList[position])
                }
            }
        }
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

        getAttrazioneImage(currentItem.id) { imagePath ->
            setAttrazioneImage(imagePath, holder.imageView)
            val overlay: Drawable? = ContextCompat.getDrawable(holder.imageView.context, R.drawable.shadow_bg)
            holder.imageView.foreground = overlay
        }


        holder.nome.text = currentItem.nome?: ""
        holder.citta.text = currentItem.citta?: ""

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(currentItem)
        }
    }

    private fun getAttrazioneImage(attrazioneId: Int?, callback: (String) -> Unit) {
        val query = "SELECT img FROM attrazioni WHERE id = $attrazioneId"

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

    private fun setAttrazioneImage(url: String?, imageView: ImageView) {

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

                        Glide.with(imageView)
                            .load(image)
                            .into(imageView)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }

}
