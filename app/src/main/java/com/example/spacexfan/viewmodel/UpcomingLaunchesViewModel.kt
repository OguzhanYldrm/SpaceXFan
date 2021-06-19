package com.example.spacexfan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.model.modelproperties.Flickr
import com.example.spacexfan.model.modelproperties.Links
import com.example.spacexfan.model.modelproperties.Patch
import com.example.spacexfan.model.modelproperties.Reddit

class UpcomingLaunchesViewModel : ViewModel() {

    val launches = MutableLiveData<List<UpcomingModel>>()
    val launchNotFound = MutableLiveData<Boolean>()
    val launchesLoading = MutableLiveData<Boolean>()

    fun refreshLaunches(){
        val launchList = arrayListOf<UpcomingModel>()

        val upcoming = UpcomingModel(
            Links(
                Patch("",""),
                Reddit("",""),
                Flickr(listOf(), listOf()), "", "ASD", ""),
            "10.10.2021",
            "Falcon 9",
            "Launched to the moon",
            100,
            "Starlink 20",
            "10000")

        launchList.add(upcoming)

        launches.value = launchList
        launchNotFound.value = false
        launchesLoading.value = false
    }

}
