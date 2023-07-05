import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RistorantiData(
    var image: Int,

    var image2: Int,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("posizione")
    var posizione: String,

    @SerializedName("rating")
    var rating: Float
)
