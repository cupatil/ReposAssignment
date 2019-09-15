package com.us.assignment.ui.location

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.us.assignment.R
import java.util.*

class LocationAdapter(
    private val connectionList: ArrayList<LocationPojo>
) : RecyclerView.Adapter<LocationAdapter.MyViewHolder>() {

    var mItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_location, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvLocation.text = connectionList[position].location
    }

    override fun getItemCount(): Int {
        return connectionList.size
    }

    fun setOnItemClickListener(mItemClickListener: OnItemClickListener) {
        this.mItemClickListener = mItemClickListener
    }

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int)
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var tvLocation: TextView = view.findViewById(R.id.tvLocation)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            if (mItemClickListener != null) {
                mItemClickListener!!.onItemClick(v, adapterPosition)
            }
        }
    }
}