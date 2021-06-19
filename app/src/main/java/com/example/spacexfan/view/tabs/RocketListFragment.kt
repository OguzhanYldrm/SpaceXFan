package com.example.spacexfan.view.tabs

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.fragment_rocket_list.*

class RocketListFragment : Fragment() {

    private lateinit var viewModel : RocketListViewModel
    private val recyclerRocketListAdapter = RocketListAdapter(arrayListOf(), this)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_list, container, false)
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

}