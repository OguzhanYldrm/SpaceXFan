package com.example.spacexfan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.model.modelproperties.Diameter
import com.example.spacexfan.model.modelproperties.Height
import com.example.spacexfan.model.modelproperties.Mass
import com.example.spacexfan.service.SpaceXAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RocketListViewModel : ViewModel() {

    private val api : SpaceXAPIService = SpaceXAPIService()
    private val disposable = CompositeDisposable()

    val rockets = MutableLiveData<List<RocketModel>>()
    val rocketNotFound = MutableLiveData<Boolean>()
    val rocketsLoading = MutableLiveData<Boolean>()

    fun refreshRockets(){

        rocketsLoading.value = true

        disposable.add(
            api.getAllRockets()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<RocketModel>>() {
                    override fun onSuccess(t: List<RocketModel>) {
                        rockets.value = t
                        rocketNotFound.value = false
                        rocketsLoading.value = false
                        println("Successfull")
                    }

                    override fun onError(e: Throwable) {
                        rocketNotFound.value = true
                        rocketsLoading.value = false
                        Log.e("HTTP ERROR : ", e.message.toString())
                        println("Unsuccessfull")
                    }

                })
        )
    }

}