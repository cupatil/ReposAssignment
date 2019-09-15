package com.us.assignment.ui.direction


import com.us.assignment.networking.ApiClient
import com.us.assignment.networking.NetworkService
import com.us.assignment.ui.direction.pojo.DirectionResults

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapDirectionPresenterImp(private val view: IMapDirectionView) : IMapDirectionPresenter {

    override fun getMapPath(origin: String, destination: String, key: String) {
        view.showWait()
        val apiService = ApiClient.googlePlacesClient.create(NetworkService::class.java)
        val call = apiService.getMapPathJson(origin, destination, key)
        call.enqueue(object : Callback<DirectionResults> {
            override fun onResponse(
                call: Call<DirectionResults>,
                response: Response<DirectionResults>
            ) {

                view.removeWait()
                view.mapPathResponse(response.body()!!)

            }

            override fun onFailure(call: Call<DirectionResults>, t: Throwable) {
                view.removeWait()
                view.onFailure(t.message!!)
            }
        })


    }
}
