package com.example.spacexfan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel

class RocketListViewModel : ViewModel() {
    val rockets = MutableLiveData<List<RocketModel>>()
    val rocketNotFound = MutableLiveData<Boolean>()
    val rocketsLoading = MutableLiveData<Boolean>()

    fun refreshRockets(){



        val rocketList = arrayListOf<RocketModel>()

        rockets.value = rocketList
        rocketNotFound.value = false
        rocketsLoading.value = false
    }

}