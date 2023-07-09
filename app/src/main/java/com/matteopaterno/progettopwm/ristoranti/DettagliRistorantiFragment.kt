package com.matteopaterno.progettopwm.ristoranti

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliRistorantiBinding
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream

class DettagliRistorantiFragment : Fragment() {
    private lateinit var binding: FragmentDettagliRistorantiBinding
    private var ristorante: RistorantiData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentDettagliRistorantiBinding.inflate(inflater, container, false)

        binding.textNome.text = ristorante?.nome
        binding.textPosizione.text = ristorante?.posizione
        binding.ratingBar.rating = ristorante?.rating!!
        val ristoranteId = ristorante?.id


        binding.menu.setOnClickListener {
            val menuFragment = parentFragmentManager.findFragmentByTag("MenuRistorante")

            if (menuFragment == null) {
                val startFragmentIsMenu = MenuRistorante()
                parentFragmentManager.beginTransaction()
                    .replace(binding.fragmentContainerView.id, startFragmentIsMenu, "MenuRistorante")
                    .commit()
            }
        }

        binding.recensioni.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(binding.fragmentContainerView.id, RecensioniRistorante())?.commit()
        }

        binding.info.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(binding.fragmentContainerView.id, InfoRistorante())?.commit()
        }

        binding.menu.callOnClick()


        getRistoranteImage(ristoranteId)
        val overlay: Drawable = resources.getDrawable(R.drawable.shadow_bg)
        binding.imageHotel.foreground = overlay
        return binding.root
    }

    private fun getRistoranteImage(ristoranteId: Int?) {

        val query = "SELECT img FROM restaurants WHERE id = $ristoranteId"

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
                                setRistoranteImage(imagePath)
                            }
                        }


                    }
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            }
        )
    }

    private fun setRistoranteImage(url: String?) {
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
                        binding.imageHotel.setImageBitmap(image)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    t.printStackTrace()
                    t.printStackTrace()
                    Toast.makeText(activity, "Richiesta fallita", Toast.LENGTH_SHORT).show()
                }
            }
        )
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ristorante?.let { RistorantiDataListHolder.RistorantiDataList }
    }


    fun setRistorante(ristorante: RistorantiData) {
        this.ristorante = ristorante
    }

    companion object {
        fun newInstance(ristorante: RistorantiData): DettagliRistorantiFragment {
            val fragment = DettagliRistorantiFragment()
            fragment.setRistorante(ristorante)
            return fragment
        }
    }
}
