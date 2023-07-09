import com.google.gson.annotations.SerializedName

data class AttrazioniData(
    var image: Int,

    @SerializedName("nome")
    var nome: String,

    @SerializedName("posizione")
    var posizione: String,

    @SerializedName("rating")
    var rating: Float
)