package com.example.spacexfan.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.modelproperties.Diameter
import com.example.spacexfan.model.modelproperties.Height
import com.example.spacexfan.model.modelproperties.Mass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FavouriteRocketsViewModel : ViewModel() {

    val rockets = MutableLiveData<List<RocketModel>>()
    val rocketNotFound = MutableLiveData<Boolean>()
    val rocketsLoading = MutableLiveData<Boolean>()
    private var mFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()


    fun refreshFavouriteRockets(): MutableLiveData<List<RocketModel>> {
        rocketsLoading.value = true
        rocketNotFound.value = false
        val getRocketList: MutableList<RocketModel> = mutableListOf()
        val collRef =
            mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites")

        collRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Log.e("Firebase Error", "Listen failed.")
                rockets.value =  mutableListOf()
                rocketNotFound.value = true
                rocketsLoading.value = false
            } else {
                if (snapshot != null) {
                    if (!snapshot.isEmpty) {
                        val documents = snapshot.documents
                        if (documents.size == 1){
                            rocketNotFound.value = true
                        }
                        for (document in documents) {
                            if (document.get("id") == null){
                                continue
                            }
                            val rocketItem = RocketModel(
                                Height(document.get("height") as Double),
                                Diameter(document.get("diameter") as Double),
                                Mass(document.get("mass") as Long),
                                document.get("flickr_images") as List<String>,
                                document.get("name") as String,
                                document.get("type") as String,
                                document.get("active") as Boolean,
                                document.get("cost_per_launch") as Long,
                                document.get("success_rate_pct") as Long,
                                document.get("first_flight") as String,
                                document.get("company") as String,
                                document.get("wikipedia") as String,
                                document.get("description") as String,
                                document.get("id") as String)

                            //Rocket add
                            getRocketList.add(rocketItem)
                        }
                        rockets.value = getRocketList

                    }
                }
            }
        }

        rocketsLoading.value = false

        return rockets


    }

    fun deleteFavouriteRocket(id : String){
        val docRef = mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites").document(id)
        docRef
            .delete()
            .addOnSuccessListener { refreshFavouriteRockets() }
            .addOnFailureListener { e -> Log.e("Firebase Error", "Error deleting document", e) }
    }

}
