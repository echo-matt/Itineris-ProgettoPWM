package com.matteopaterno.progettopwm.hotel

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class HotelData(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("nome")
    var nome: String?,

    @SerializedName("posizione")
    var posizione: String?,

    @SerializedName("rating")
    var rating: Float?,

    @SerializedName("citta")
    var citta: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float,
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(nome)
        parcel.writeString(posizione)
        parcel.writeValue(rating)
        parcel.writeString(citta)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HotelData> {
        override fun createFromParcel(parcel: Parcel): HotelData {
            return HotelData(parcel)
        }

        override fun newArray(size: Int): Array<HotelData?> {
            return arrayOfNulls(size)
        }
    }
}
