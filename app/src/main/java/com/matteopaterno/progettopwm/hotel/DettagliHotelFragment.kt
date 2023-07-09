package com.matteopaterno.progettopwm.hotel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.JsonObject
import com.matteopaterno.progettopwm.R
import com.matteopaterno.progettopwm.databinding.FragmentDettagliHotelBinding
import com.matteopaterno.progettopwm.info.InfoFragment
import com.matteopaterno.progettopwm.prenotazioni.PrenotazioneFragment
import com.matteopaterno.progettopwm.recensioni.RecensioniFragment
import com.matteopaterno.progettopwm.retrofit.ClientNetwork
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayInputStream


class DettagliHotelFragment : Fragment() {

    private lateinit var binding: FragmentDettagliHotelBinding
    private var hotel: HotelData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDettagliHotelBinding.inflate(layoutInflater)


        binding.textNome.text = hotel?.nome
        binding.textPosizione.text = hotel?.posizione
        binding.textCitta.text = hotel?.citta
        binding.ratingBar.rating = hotel?.rating!!
        var hotelId = hotel?.id


        binding.bottonePrenota.setOnClickListener{

            val fragment = PrenotazioneFragment.newInstance(hotel!!)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_container, fragment)
                .addToBackStack("Fragment Prenotazioni")
                .commit()
        }


        binding.recensioniButton.setOnClickListener {
            val fragment = RecensioniFragment.newInstance(hotel!!)
            val transaction = requireActivity().supportFragmentManager.beginTransaction()

            transaction.replace(binding.fragmentContainerView.id, fragment)
                .addToBackStack("Fragment Recensioni")
                .commit()
        }



        binding.infoButton.setOnClickListener {
            val transaction = activity?.supportFragmentManager?.beginTransaction()
            transaction?.replace(binding.fragmentContainerView.id, InfoFragment())?.commit()
        }

        getHotelImage(hotelId)
        getServiceImage(hotelId)



        return binding.root

    }

    private fun getHotelImage(hotelId: Int?) {

        val query = "SELECT img FROM hotels WHERE id = $hotelId"

        ClientNetwork.retrofit.select(query).enqueue(
            object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    if (response.isSuccessful) {
                        val jsonResponse = response.body()
                        val jsonArray = jsonResponse?.getAsJsonArray("queryset")
                        if (jsonArray != null && jsonArray.size() > 0) {
                            val jsonObject = jsonArray.get(0).asJsonObject
                            if (jsonObject != null) {
                                val imagePath = jsonObject.get("img").asString
                                setHotelImage(imagePath)
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

    private fun setHotelImage(url: String?) {
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


    private fun getServiceImage(hotelId: Int?) {

        val query = "SELECT s.img, s.nome FROM servizi AS s INNER JOIN servizi_hotel AS hs ON s.id = hs.servizi_id WHERE hs.hotel_id = $hotelId"

        ClientNetwork.retrofit.select(query).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    val jsonResponse = response.body()
                    val servicesArray = jsonResponse?.getAsJsonArray("queryset")

                    servicesArray?.let { services ->
                        for (i in 0 until services.size()) {
                            val serviceObject = services.get(i).asJsonObject

                            if (serviceObject.has("img") && !serviceObject.get("img").isJsonNull &&
                                serviceObject.has("nome") && !serviceObject.get("nome").isJsonNull
                            ) {
                                val imagePath = serviceObject.get("img").asString
                                val serviceName = serviceObject.get("nome").asString
                                if (!imagePath.isNullOrEmpty() && !serviceName.isNullOrEmpty()) {
                                    val imageView = getServiceImageView(i)
                                    val textView = getServiceNameTextView(i)
                                    setProfileImg(imagePath, imageView)
                                    setServiceName(serviceName, i)
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(activity, "Errore nel recuperare i dati", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                t.printStackTrace()
                Toast.makeText(activity, "Errore nella richiesta", Toast.LENGTH_SHORT).show()
            }
        })
    }


    private fun getServiceImageView(serviceId: Int): ImageView {
        return when (serviceId) {
            0 -> binding.image1
            1 -> binding.image2
            2 -> binding.image3
            3 -> binding.image4
            4 -> binding.image5
            5 -> binding.image6
            6 -> binding.image7
            7 -> binding.image8
            else -> binding.image1
        }
    }

    private fun setServiceName(serviceName: String, serviceIndex: Int) {
        val textView = getServiceNameTextView(serviceIndex)
        textView.text = serviceName
    }

    private fun getServiceNameTextView(serviceIndex: Int): TextView {
        return when (serviceIndex) {
            0 -> binding.textViewService1
            1 -> binding.textViewService2
            2 -> binding.textViewService3
            3 -> binding.textViewService4
            4 -> binding.textViewService5
            5 -> binding.textViewService6
            6 -> binding.textViewService7
            7 -> binding.textViewService8
            else -> throw IllegalArgumentException("Indice servizio non valido: $serviceIndex")
        }
    }


    private fun setProfileImg(url: String, imageView: ImageView) {
        if (imageView != null){
            ClientNetwork.retrofit.image(url).enqueue(
                object : Callback<ResponseBody> {
                    override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            val bitmap: Bitmap? = BitmapFactory.decodeStream(response.body()?.byteStream())
                            if (bitmap != null) {
                                imageView.setImageBitmap(bitmap)
                            } else {
                                Log.d("ImageDecode", "Bitmap null")
                            }
                        } else {
                            Log.d("ImageResponse", "Richiesta fallita, codice: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    }
                }
            )
        }else {
            Log.d("ImageView", "ImageView null")
        }

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hotel?.let { HotelDataListHolder.hotelDataList }

    }

    fun setHotel(hotel: HotelData){
        this.hotel = hotel
    }

    companion object{
        fun newInstance(hotel: HotelData): DettagliHotelFragment{
            val fragment = DettagliHotelFragment()
            fragment.setHotel(hotel)
            return fragment
        }
    }

}