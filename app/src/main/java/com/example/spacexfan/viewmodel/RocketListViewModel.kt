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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class RocketListViewModel : ViewModel() {

    private val api : SpaceXAPIService = SpaceXAPIService()
    private val disposable = CompositeDisposable()


    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
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
                    }

                    override fun onError(e: Throwable) {
                        rocketNotFound.value = true
                        rocketsLoading.value = false
                        Log.e("HTTP ERROR : ", e.message.toString())
                    }

                })
        )
    }

    fun deleteFavouriteRocket(id : String){
        val docRef = mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites").document(id)
        docRef
            .delete()
            .addOnSuccessListener { refreshRockets() }
            .addOnFailureListener { e -> Log.e("Firebase Error", "Error deleting document", e) }
    }

}