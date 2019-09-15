@file:Suppress("DEPRECATION")

package com.us.assignment.util

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions
import com.us.assignment.AssignmentApplication
import com.us.assignment.R
import com.us.assignment.ui.direction.pojo.DirectionResults
import com.us.assignment.ui.direction.pojo.Location
import com.us.assignment.ui.direction.pojo.StepsItem
import java.util.*

object ProjectUtilities {

    var mLastKnownLocation: android.location.Location? = null

    private var pDialog: ProgressDialog? = null

    // Define variable
    private var isInternetPresent: Boolean? = false
    @SuppressLint("StaticFieldLeak")
    internal lateinit var cd: ConnectionDetector


    // This method is for checking internet connection
    fun checkInternetAvailable(mContext: Context): Boolean? {
        cd =
            ConnectionDetector(mContext)
        isInternetPresent = cd.isConnectingToInternet

        return isInternetPresent

    }

    //This method show progress dialog
    fun showProgressDialog(mContext: Context) {
        pDialog = ProgressDialog(mContext)
        pDialog!!.setMessage("Please wait...")
        pDialog!!.setCancelable(false)
        pDialog!!.show()
    }

    //This method dismiss progress dialog
    fun dismissProgressDialog() {
        if (pDialog != null && pDialog!!.isShowing)
            pDialog!!.dismiss()
    }

    fun showAlertDialog(mContext: Context?, message: String) {

        if (mContext != null) {

            val alertDialog = AlertDialog.Builder(mContext)
            alertDialog.setCancelable(false)

            alertDialog.setMessage(message)

            alertDialog.setPositiveButton(
                mContext.resources.getString(R.string.btn_ok)
            ) { dialog, which ->

                dialog.cancel()
            }
            alertDialog.show()
        }
    }

    fun getPathForMap(response: DirectionResults): PolylineOptions {
        var mileage = ""
        val routelist = ArrayList<LatLng>()
        //PolylineOptions rectLine = new PolylineOptions().width(10).color(CargoApplication.getInstance().getColor(R.color.colorPrimary));

        val rectLine = PolylineOptions().width(10f).color(
            ContextCompat.getColor(
                AssignmentApplication.instance!!,
                R.color.colorPrimary
            )
        )
        if (response.routes!!.isNotEmpty()) {
            var decodelist: ArrayList<LatLng>
            val routeA = response.routes[0]
            Log.i("zacharia", "Legs length : " + routeA!!.legs!!.size)
            if (routeA.legs!!.isNotEmpty()) {
                mileage = "" + routeA.legs[0]!!.distance!!.value
                val steps = routeA.legs[0]!!.steps
                Log.i("zacharia", "Steps size :" + steps!!.size)
                var step: StepsItem
                var location: Location
                var polyline: String
                for (i in steps.indices) {
                    step = steps[i]!!
                    location = step.startLocation!!
                    routelist.add(LatLng(location.lat!!, location.lng!!))
                    Log.i("zacharia", "Start Location :" + location.lat + ", " + location.lng)
                    polyline = step.polyline!!.points!!
                    decodelist = decodePoly(polyline)
                    routelist.addAll(decodelist)
                    location = step.endLocation!!
                    routelist.add(LatLng(location.lat!!, location.lng!!))
                    Log.i("zacharia", "End Location :" + location.lat + ", " + location.lng)
                }
            }
        }
        Log.i("zacharia", "routelist size : " + routelist.size)
        if (routelist.size > 0) {

            for (i in routelist.indices) {
                rectLine.add(routelist[i])
            }
        }

        return rectLine

    }

    private fun decodePoly(encoded: String): ArrayList<LatLng> {
        val poly = ArrayList<LatLng>()
        var index = 0
        val len = encoded.length
        var lat = 0
        var lng = 0
        while (index < len) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lat += dlat
            shift = 0
            result = 0
            do {
                b = encoded[index++].toInt() - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
            lng += dlng

            val position = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(position)
        }
        return poly
    }
}
