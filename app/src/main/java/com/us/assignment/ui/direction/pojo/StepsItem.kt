package com.us.assignment.ui.direction.pojo


import com.google.gson.annotations.SerializedName


data class StepsItem(

    @field:SerializedName("duration")
    val duration: Duration? = null,

    @field:SerializedName("start_location")
    val startLocation: Location? = null,

    @field:SerializedName("distance")
    val distance: Distance? = null,

    @field:SerializedName("travel_mode")
    val travelMode: String? = null,

    @field:SerializedName("html_instructions")
    val htmlInstructions: String? = null,

    @field:SerializedName("end_location")
    val endLocation: Location? = null,

    @field:SerializedName("polyline")
    val polyline: Polyline? = null
)