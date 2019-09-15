package com.us.assignment.ui.direction.pojo


import com.google.gson.annotations.SerializedName


data class Location(

    @field:SerializedName("lng")
    val lng: Double? = null,

    @field:SerializedName("lat")
    val lat: Double? = null
)