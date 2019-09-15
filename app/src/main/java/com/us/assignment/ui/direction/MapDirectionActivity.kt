package com.us.assignment.ui.direction

import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.us.assignment.R
import com.us.assignment.base.BaseLocationClass
import com.us.assignment.ui.direction.pojo.DirectionResults
import com.us.assignment.ui.location.LocationPojo
import com.us.assignment.util.ProjectUtilities
import com.us.assignment.util.ProjectUtilities.mLastKnownLocation


class MapDirectionActivity : BaseLocationClass(), IMapDirectionView, OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var locationPojo: LocationPojo
    private lateinit var iJobDetailPresenter: IMapDirectionPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_direction)

        locationPojo = intent.getParcelableExtra("location")

        initObject()

        supportActionBar!!.title = locationPojo.location
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun initObject() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        iJobDetailPresenter = MapDirectionPresenterImp(this)
    }

    override fun onLocationChanged(location: Location) {
        super.onLocationChanged(location)

        if (mLastKnownLocation == null) {
            mLastKnownLocation = location

            updateLocationUI()

            // Get the current location of the device and set the position of the map.
            getDeviceLocation()
        }
    }

    override fun onMapReady(map: GoogleMap?) {
        googleMap = map

        val sydney = LatLng(locationPojo.latitude, locationPojo.longitude)
        googleMap!!.addMarker(
            MarkerOptions().position(sydney)
                .title(locationPojo.location)
        )
        googleMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        if (mLastKnownLocation != null) {
            updateLocationUI()

            // Get the current location of the device and set the position of the map.
            getDeviceLocation()
        }

    }

    private fun updateLocationUI() {
        if (googleMap == null) {
            return
        }
        try {

            googleMap!!.isMyLocationEnabled = true
            googleMap!!.uiSettings.isMyLocationButtonEnabled = true

        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message)
        }

    }

    private fun getDeviceLocation() {

        if (mLastKnownLocation != null) {
            val sydney = LatLng(
                mLastKnownLocation!!.latitude,
                mLastKnownLocation!!.longitude
            )
            googleMap!!.addMarker(
                MarkerOptions().position(sydney)
                    .title("Home")
            )

            googleMap!!.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        mLastKnownLocation!!.latitude,
                        mLastKnownLocation!!.longitude
                    ), 15F
                )


            )
            val destination = "" + locationPojo.latitude + "," + locationPojo.longitude
            val source =
                "" + mLastKnownLocation!!.latitude + "," + mLastKnownLocation!!.longitude

            iJobDetailPresenter.getMapPath(
                source,
                destination,
                resources.getString(R.string.google_maps_key)
            )
        }
    }

    override fun mapPathResponse(response: DirectionResults) {

        if (response.routes!!.isEmpty()) {
            ProjectUtilities.showAlertDialog(this@MapDirectionActivity, "Path not found.")
        } else {
            val rectLine = ProjectUtilities.getPathForMap(response)
            // Adding route on the map
            googleMap!!.addPolyline(rectLine)
        }

    }

    override fun showWait() {
        ProjectUtilities.showProgressDialog(this)
    }

    override fun removeWait() {
        ProjectUtilities.dismissProgressDialog()
    }


    override fun onFailure(appErrorMessage: String) {
        Toast.makeText(this, appErrorMessage, Toast.LENGTH_LONG).show()
    }

}
