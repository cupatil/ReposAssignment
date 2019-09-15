package com.us.assignment.ui.direction


import com.us.assignment.base.IBaseView
import com.us.assignment.ui.direction.pojo.DirectionResults


interface IMapDirectionView : IBaseView {

    fun mapPathResponse(response: DirectionResults)

}
