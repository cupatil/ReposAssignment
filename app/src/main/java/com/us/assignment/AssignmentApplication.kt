package com.us.assignment

import android.app.Application


import com.us.assignment.networking.ApiClient
import com.us.assignment.networking.NetworkService

class AssignmentApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        netWorkServiceObject = ApiClient.googlePlacesClient.create(NetworkService::class.java)
    }

    companion object {

        var instance: AssignmentApplication? = null
            private set

        var netWorkServiceObject: NetworkService? = null
            private set
    }
}
