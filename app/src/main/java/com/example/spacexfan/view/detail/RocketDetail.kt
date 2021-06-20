package com.example.spacexfan.view.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.spacexfan.R
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.viewmodel.RocketDetailViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_rocket_detail.*
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class RocketDetail : AppCompatActivity() {

    private lateinit var viewModel: RocketDetailViewModel
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_detail)

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        val intent = intent
        val rocketData: RocketModel = intent.getSerializableExtra("Rocket") as RocketModel

        //Fav Button Operations
        //Add fav
        favourite_button_detail.setOnClickListener {
            onFavouriteClick(rocketData)
        }
        //Button State
        favouriteButtonSetState(rocketData)

        rocketData.flickrImages.forEach {
            carousel.addData(CarouselItem(it))
        }
        
        viewModel = ViewModelProviders.of(this).get(RocketDetailViewModel::class.java)
        viewModel.getData(rocketData)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.rocketDetailLiveData.observe(this, { rocketDetail ->
            rocketDetail?.let {
                detail_name.text = it.name
                detail_active.text = it.active.toString()
                detail_company.text = it.company
                detail_cost.text = it.costPerLaunch.toString()
                detail_description.text = it.description
                detail_diameter.text = it.diameter.toString()
                detail_height.text = it.height.toString()
                detail_mass.text = it.mass.toString()
                detail_first_flight.text = it.firstFlight
                detail_success.text = it.successRatePct.toString()
                detail_type.text = it.type
            }

        })
    }

    fun onFavouriteClick(data: RocketModel){

        val favRocket = hashMapOf<String, Any>()
        favRocket["height"] = data.height.meters
        favRocket["diameter"] = data.diameter.meters
        favRocket["mass"] = data.mass.kg
        favRocket["flickr_images"] = data.flickrImages
        favRocket["name"] = data.name
        favRocket["type"] = data.type
        favRocket["active"] = data.active
        favRocket["cost_per_launch"] = data.costPerLaunch
        favRocket["success_rate_pct"] = data.successRatePct
        favRocket["first_flight"] = data.firstFlight
        favRocket["company"] = data.company
        favRocket["wikipedia"] = data.wikipedia
        favRocket["description"] = data.description
        favRocket["id"] = data.id

        val docIdRef = mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites").document(data.id)
        val collRef = mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites")

        docIdRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                if (!document!!.exists()) {
                    collRef.document(data.id).set(favRocket)
                    favourite_button_detail.isSelected = true
                }
            } else {
                Log.d("Firebase", "Failed with: ", task.exception)
            }
        }

    }

    private fun favouriteButtonSetState(data : RocketModel) {
        val docIdRef =
            mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites")
                .document(data.id)
        docIdRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                favourite_button_detail.isSelected = document!!.exists()
            } else {
                Log.d("Firebase", "Failed with: ", task.exception)
            }
        }
    }
}