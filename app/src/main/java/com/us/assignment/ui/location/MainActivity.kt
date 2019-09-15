package com.us.assignment.ui.location

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import com.us.assignment.R
import com.us.assignment.base.BaseLocationClass
import com.us.assignment.ui.direction.MapDirectionActivity
import com.us.assignment.util.ProjectUtilities
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseLocationClass() {

    private var locationList = ArrayList<LocationPojo>()
    private lateinit var locatioAdapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setLocation()

        initView()
    }

    private fun initView() {
        locatioAdapter = LocationAdapter(locationList)
        recyclerView.adapter = locatioAdapter

        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                DividerItemDecoration.VERTICAL
            )
        )

        locatioAdapter.setOnItemClickListener(object :
            LocationAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {

                val intent = Intent(this@MainActivity, MapDirectionActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable("location", locationList[position])
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })


    }

    override fun onLocationChanged(location: Location) {
        super.onLocationChanged(location)

        if (ProjectUtilities.mLastKnownLocation == null) {
            ProjectUtilities.mLastKnownLocation = location
        }
    }


    private fun setLocation() {
        locationList.clear()
        locationList.add(
            LocationPojo(
                "Taj Mahal",
                27.171165982,
                78.038666512
            )
        )
        locationList.add(
            LocationPojo(
                "India Gate",
                28.6077159025,
                77.224249103
            )
        )
        locationList.add(
            LocationPojo(
                "Gateway of India",
                18.921836,
                72.834705
            )
        )
        locationList.add(LocationPojo("Srinagar", 34.083656, 74.797371))
        locationList.add(LocationPojo("Margao", 15.286691, 73.969780))
        locationList.add(
            LocationPojo(
                "Pondicherry",
                11.916064,
                79.812325
            )
        )
        locationList.add(LocationPojo("Hyderabad", 17.387140, 78.491684))
        locationList.add(LocationPojo("Kolkata", 22.572645, 88.363892))
        locationList.add(LocationPojo("Patna", 25.612677, 85.158875))
        locationList.add(LocationPojo("Guwahati", 26.148043, 91.731377))
    }
}
