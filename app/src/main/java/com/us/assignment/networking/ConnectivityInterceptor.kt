package com.us.assignment.networking

import com.us.assignment.AssignmentApplication
import com.us.assignment.util.ProjectUtilities
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


class ConnectivityInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!ProjectUtilities.checkInternetAvailable(AssignmentApplication.instance!!)!!) {
            throw NoConnectivityException()
        }

        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
