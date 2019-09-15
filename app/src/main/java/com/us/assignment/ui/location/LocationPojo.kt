@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package com.us.assignment.ui.location

import android.os.Parcel
import android.os.Parcelable

data class LocationPojo(var location: String, var latitude: Double, var longitude: Double) :
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(location)
        parcel.writeDouble(latitude)
        parcel.writeDouble(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LocationPojo> {
        override fun createFromParcel(parcel: Parcel): LocationPojo {
            return LocationPojo(parcel)
        }

        override fun newArray(size: Int): Array<LocationPojo?> {
            return arrayOfNulls(size)
        }
    }
}