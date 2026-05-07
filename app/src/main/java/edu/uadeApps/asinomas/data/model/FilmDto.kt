package edu.uadeApps.asinomas.data.model

import com.google.gson.annotations.SerializedName

data class ResourceDto(
    val name: String? = null,
    val title: String? = null,
    val url: String,
    @SerializedName("created") val created: String? = null,
    @SerializedName("edited") val edited: String? = null
)