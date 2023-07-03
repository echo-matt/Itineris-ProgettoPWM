import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class AttrazioniData(
    var image: Int,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("posizione")
    var posizione: String,

    @SerializedName("rating")
    var rating: Float
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(image)
        parcel.writeString(nome)
        parcel.writeString(posizione)
        parcel.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AttrazioniData> {
        override fun createFromParcel(parcel: Parcel): AttrazioniData {
            return AttrazioniData(parcel)
        }

        override fun newArray(size: Int): Array<AttrazioniData?> {
            return arrayOfNulls(size)
        }
    }
}
