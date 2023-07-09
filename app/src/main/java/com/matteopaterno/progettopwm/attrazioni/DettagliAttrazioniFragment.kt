package com.matteopaterno.progettopwm.attrazioni

import AttrazioniData
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliAttrazioniBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class DettagliAttrazioniFragment : Fragment() {
    private lateinit var binding: FragmentDettagliAttrazioniBinding
    private var attrazione: AttrazioniData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDettagliAttrazioniBinding.inflate(inflater, container, false)

        binding.textNome.text = attrazione?.nome
        binding.textPosizione.text = attrazione?.citta
        binding.descrizione.text = attrazione?.descrizione

        getAttrazioneImage(attrazione?.id)

        val overlay: Drawable = resources.getDrawable(R.drawable.shadow_bg)
        binding.imageAttrazione.foreground = overlay

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        attrazione?.let { AttrazioniDataListHolder.AttrazioniDataList }
    }

    private fun getAttrazioneImage(attrazioneId: Int?) {
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
                                setAttrazioneImage(imagePath)
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

    private fun setAttrazioneImage(url: String?) {

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

                        Glide.with(binding.imageAttrazione)
                            .load(image)
                            .into(binding.imageAttrazione)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                }
            }
        )
    }


    fun setAttrazione(attrazione: AttrazioniData) {
        this.attrazione = attrazione
    }

    companion object {
        fun newInstance(attrazione: AttrazioniData): DettagliAttrazioniFragment {
            val fragment = DettagliAttrazioniFragment()
            fragment.setAttrazione(attrazione)
            return fragment
        }
    }
}
