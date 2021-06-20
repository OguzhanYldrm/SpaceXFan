package com.example.spacexfan.view.tabs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan.R
import com.example.spacexfan.adapter.FavouriteRocketsAdapter
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.utils.loadGif
import com.example.spacexfan.view.detail.RocketDetail
import com.example.spacexfan.viewmodel.FavouriteRocketsViewModel
import com.example.spacexfan.viewmodel.RocketListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_favourite_rockets.*
import kotlinx.android.synthetic.main.fragment_rocket_list.*

class FavouriteRocketsFragment : Fragment() {

    private lateinit var viewModel : FavouriteRocketsViewModel
    private val recyclerFavouriteRocketsAdapter = FavouriteRocketsAdapter(arrayListOf(), this)
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_favourite_rockets, container, false)

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading_favourite.loadGif()

        viewModel = ViewModelProviders.of(this).get(FavouriteRocketsViewModel::class.java)
        viewModel.refreshFavouriteRockets()

        favourite_rockets_recycler.layoutManager = LinearLayoutManager(context)
        favourite_rockets_recycler.adapter = recyclerFavouriteRocketsAdapter

        fav_rockets_refresh.setOnRefreshListener {
            loading_favourite.visibility = View.VISIBLE
            no_favourite.visibility = View.INVISIBLE
            favourite_rockets_recycler.visibility = View.INVISIBLE
            viewModel.refreshFavouriteRockets()
            fav_rockets_refresh.isRefreshing = false
        }


        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.rockets.observe(viewLifecycleOwner, { currentRockets ->
            currentRockets?.let {
                favourite_rockets_recycler.visibility = View.VISIBLE
                recyclerFavouriteRocketsAdapter.updateRocketList(currentRockets)
            }
        })

        viewModel.rocketNotFound.observe(viewLifecycleOwner, { isNotFound ->
            isNotFound?.let {
                if (it) {
                    no_favourite.visibility = View.VISIBLE
                    favourite_rockets_recycler.visibility = View.INVISIBLE
                } else {
                    no_favourite.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.rocketsLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) {
                    loading_favourite.visibility = View.VISIBLE
                    no_favourite.visibility = View.INVISIBLE
                    favourite_rockets_recycler.visibility = View.INVISIBLE
                } else {
                    loading_favourite.visibility = View.INVISIBLE
                }
            }

        })
    }
    fun onDetailClick(data: RocketModel) {
        val intent = Intent(context, RocketDetail::class.java)
        intent.putExtra("Rocket", data)
        context?.let { ContextCompat.startActivity(it, intent, null) }
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
                    favourite_rockets_recycler.adapter?.notifyDataSetChanged()
                } else{
                    viewModel.deleteFavouriteRocket(data.id)
                    favourite_rockets_recycler.adapter?.notifyDataSetChanged()
                }
            } else {
                Log.d("Firebase", "Failed with: ", task.exception)
            }
        }

    }



}