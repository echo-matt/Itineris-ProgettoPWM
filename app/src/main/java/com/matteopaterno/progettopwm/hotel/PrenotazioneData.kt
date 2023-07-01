package com.matteopaterno.progettopwm.hotel

import com.google.gson.annotations.SerializedName
import java.util.Date

data class PrenotazioneData(
    @SerializedName("id")
    val id: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("hotel_id")
    val hotelId: String,
    @SerializedName("check_in_date")
    val checkInDate: Date,
    @SerializedName("check_out_date")
    val checkOutDate: Date,
    @SerializedName("payment)status")
    var paymentStatus: PaymentStatus
)

enum class PaymentStatus {
    PENDING,
    SUCCESSFUL,
    FAILED
}
