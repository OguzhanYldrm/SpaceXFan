package com.example.spacexfan.view.tabs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan.R
import com.example.spacexfan.adapter.RocketListAdapter
import com.example.spacexfan.viewmodel.RocketListViewModel
import kotlinx.android.synthetic.main.fragment_rocket_list.*

class RocketListFragment : Fragment() {

    private lateinit var viewModel : RocketListViewModel
    private val recyclerRocketListAdapter = RocketListAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rocket_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RocketListViewModel::class.java)
        viewModel.refreshRockets()

        rockets_list_recycler.layoutManager = LinearLayoutManager(context)
        rockets_list_recycler.adapter = recyclerRocketListAdapter

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
                if (it){
                    not_found.visibility = View.VISIBLE
                    rockets_list_recycler.visibility = View.INVISIBLE
                } else{
                    not_found.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.rocketsLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it){
                    loading.visibility = View.VISIBLE
                    not_found.visibility = View.INVISIBLE
                    rockets_list_recycler.visibility = View.INVISIBLE
                } else{
                    loading.visibility = View.INVISIBLE
                }
            }

        })
    }

}