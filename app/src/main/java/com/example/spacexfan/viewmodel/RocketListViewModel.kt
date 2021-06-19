package com.example.spacexfan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.modelproperties.Diameter
import com.example.spacexfan.model.modelproperties.Height
import com.example.spacexfan.model.modelproperties.Mass

class RocketListViewModel : ViewModel() {
    val rockets = MutableLiveData<List<RocketModel>>()
    val rocketNotFound = MutableLiveData<Boolean>()
    val rocketsLoading = MutableLiveData<Boolean>()

    fun refreshRockets(){



        val rocketList = arrayListOf<RocketModel>()

        val rocket = RocketModel(
            Height(1000),
            Diameter(3.5),
            Mass(250000),
            listOf("a"),
            "Falcon 9",
            "Merlin",
            true,
            900000,
            90,
            "10.10.2021",
            "SpaceX",
            "",
            "Good Rocket",
            "10005")

        rocketList.add(rocket)

        rockets.value = rocketList
        rocketNotFound.value = false
        rocketsLoading.value = false
    }

}