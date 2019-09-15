package com.us.assignment.ui.direction.pojo

import com.google.gson.annotations.SerializedName


data class DirectionResults(

    @field:SerializedName("routes")
    val routes: List<RoutesItem?>? = null,

    @field:SerializedName("geocoded_waypoints")
    val geocodedWaypoints: List<GeocodedWaypointsItem?>? = null,

    @field:SerializedName("status")
    val status: String? = null
)