import com.google.gson.annotations.SerializedName

data class AttrazioniData(

    @SerializedName("id")
    var id: Int?,

    @SerializedName("nome")
    var nome: String?,

    @SerializedName("descrizione")
    var descrizione: String?,

    @SerializedName("citta")
    var citta: String?
)