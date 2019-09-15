package com.us.assignment.ui.direction.pojo


import com.google.gson.annotations.SerializedName


data class LegsItem(

    @field:SerializedName("duration")
    val duration: Duration? = null,

    @field:SerializedName("start_location")
    val startLocation: Location? = null,

    @field:SerializedName("distance")
    val distance: Distance? = null,

    @field:SerializedName("start_address")
    val startAddress: String? = null,

    @field:SerializedName("end_location")
    val endLocation: Location? = null,

    @field:SerializedName("end_address")
    val endAddress: String? = null,

    @field:SerializedName("via_waypoint")
    val viaWaypoint: List<Any?>? = null,

    @field:SerializedName("steps")
    val steps: List<StepsItem?>? = null,

    @field:SerializedName("traffic_speed_entry")
    val trafficSpeedEntry: List<Any?>? = null
)