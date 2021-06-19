package com.example.spacexfan.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.model.modelproperties.Flickr
import com.example.spacexfan.model.modelproperties.Links
import com.example.spacexfan.model.modelproperties.Patch
import com.example.spacexfan.model.modelproperties.Reddit
import com.example.spacexfan.service.SpaceXAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class UpcomingLaunchesViewModel : ViewModel() {

    private val api : SpaceXAPIService = SpaceXAPIService()
    private val disposable = CompositeDisposable()

    val launches = MutableLiveData<List<UpcomingModel>>()
    val launchNotFound = MutableLiveData<Boolean>()
    val launchesLoading = MutableLiveData<Boolean>()

    fun refreshLaunches(){
        launchesLoading.value = true

        disposable.add(
            api.getAllUpcomingLaunches()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<UpcomingModel>>() {
                    override fun onSuccess(t: List<UpcomingModel>) {
                        launches.value = t
                        launchNotFound.value = false
                        launchesLoading.value = false
                    }

                    override fun onError(e: Throwable) {
                        launchNotFound.value = true
                        launchesLoading.value = false
                    }

                })
        )
    }

}
