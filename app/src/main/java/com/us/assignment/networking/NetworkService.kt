package com.us.assignment.networking

import com.us.assignment.ui.direction.pojo.DirectionResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("/maps/api/directions/json")
    fun getMapPathJson(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<DirectionResults>

}
