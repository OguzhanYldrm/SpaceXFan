package com.example.spacexfan.view.tabs

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan.R
import com.example.spacexfan.adapter.RocketListAdapter
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.utils.loadGif
import com.example.spacexfan.view.detail.RocketDetail
import com.example.spacexfan.viewmodel.RocketListViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_favourite_rockets.*
import kotlinx.android.synthetic.main.fragment_rocket_list.*

class RocketListFragment : Fragment() {

    private lateinit var viewModel : RocketListViewModel
    private val recyclerRocketListAdapter = RocketListAdapter(arrayListOf(), this)
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirestore : FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view : View = inflater.inflate(R.layout.fragment_rocket_list, container, false)

        mFirestore = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading.loadGif()

        viewModel = ViewModelProviders.of(this).get(RocketListViewModel::class.java)
        viewModel.refreshRockets()

        rockets_list_recycler.layoutManager = LinearLayoutManager(context)
        rockets_list_recycler.adapter = recyclerRocketListAdapter

        rocket_list_refresh.setOnRefreshListener {
            loading.visibility = View.VISIBLE
            not_found.visibility = View.INVISIBLE
            rockets_list_recycler.visibility = View.INVISIBLE
            viewModel.refreshRockets()
            rocket_list_refresh.isRefreshing = false
        }


        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.rockets.observe(viewLifecycleOwner, { currentRockets ->
            currentRockets?.let {
                rockets_list_recycler.visibility = View.VISIBLE
                recyclerRocketListAdapter.updateRocketList(currentRockets)
            }
        })

        viewModel.rocketNotFound.observe(viewLifecycleOwner, { isNotFound ->
            isNotFound?.let {
                if (it) {
                    not_found.visibility = View.VISIBLE
                    rockets_list_recycler.visibility = View.INVISIBLE
                } else {
                    not_found.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.rocketsLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it) {
                    loading.visibility = View.VISIBLE
                    not_found.visibility = View.INVISIBLE
                    rockets_list_recycler.visibility = View.INVISIBLE
                } else {
                    loading.visibility = View.INVISIBLE
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
                    rockets_list_recycler.adapter?.notifyDataSetChanged()
                } else{
                    viewModel.deleteFavouriteRocket(data.id)
                    rockets_list_recycler.adapter?.notifyDataSetChanged()
                }
            } else {
                Log.d("Firebase", "Failed with: ", task.exception)
            }
        }

    }

}