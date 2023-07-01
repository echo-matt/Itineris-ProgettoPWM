package com.matteopaterno.progettopwm.retrofit

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName

data class UserModel(

    @SerializedName("nome")
    var userNome: String?,

    @SerializedName("cognome")
    var userCognome: String?,

    @SerializedName("username")
    var userUsername: String?,

    @SerializedName("password")
    var userPassword: String?,

    @SerializedName("img")
    var img: Bitmap
)

data class UserLoginModel(
    @SerializedName("username")
    var userUsername: String?,

    @SerializedName("password")
    var userPassword: String?
)

