package com.example.spacexfan.service

import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import io.reactivex.Single
import retrofit2.http.GET

interface SpaceXAPI {

    @GET("rockets")
    fun getRockets() : Single<List<RocketModel>>


    @GET("launches/upcoming")
    fun getUpcomingLaunches() : Single<List<UpcomingModel>>

}