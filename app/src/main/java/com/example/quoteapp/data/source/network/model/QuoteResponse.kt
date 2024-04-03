package com.example.quoteapp.data.source.network.model

import com.google.gson.annotations.SerializedName

data class QuoteResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("quote")
    val quote: String?,
    @SerializedName("anime")
    val anime: String?,
    @SerializedName ("character")
    val character: String?

)
