package com.example.spacexfan.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.model.modelproperties.*

class UpcomingDetailViewModel : ViewModel() {

    val upcomingDetailLiveData = MutableLiveData<UpcomingModel>()

    fun getData(){
        val upcoming = UpcomingModel(
                Links(Patch("",""),
                    Reddit("",""),
                    Flickr(listOf(), listOf()), "", "ASD", ""),
            "10.10.2021",
             "Falcon 9",
            "Launched to the moon",
            100,
            "Starlink 20",
            "10000")
        upcomingDetailLiveData.value = upcoming
    }
}