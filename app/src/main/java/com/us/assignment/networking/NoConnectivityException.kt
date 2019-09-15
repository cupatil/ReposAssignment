package com.us.assignment.networking

import com.us.assignment.AssignmentApplication
import com.us.assignment.R
import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String?
        get() = AssignmentApplication.instance!!.getString(R.string.internet_error)

}
