package com.example.spacexfan.service

import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SpaceXAPIService {

    private val BASE_URL : String = "https://api.spacexdata.com/v4/"

    private val API = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SpaceXAPI::class.java)

    fun getAllRockets() : Single<List<RocketModel>>{
        return API.getRockets()
    }

    fun getAllUpcomingLaunches() : Single<List<UpcomingModel>>{
        return API.getUpcomingLaunches()
    }

}