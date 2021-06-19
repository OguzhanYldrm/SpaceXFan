package com.example.spacexfan.view.tabs

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spacexfan.R
import com.example.spacexfan.adapter.UpcomingLaunchesAdapter
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.utils.loadGif
import com.example.spacexfan.view.detail.RocketDetail
import com.example.spacexfan.view.detail.UpcomingDetail
import com.example.spacexfan.viewmodel.UpcomingLaunchesViewModel
import kotlinx.android.synthetic.main.fragment_upcoming_launches.*

class UpcomingLaunchesFragment : Fragment() {

    private lateinit var viewModel: UpcomingLaunchesViewModel
    private val recyclerUpcomingLaunchesAdapter = UpcomingLaunchesAdapter(arrayListOf(), this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming_launches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading_launch.loadGif()
        viewModel = ViewModelProviders.of(this).get(UpcomingLaunchesViewModel::class.java)
        viewModel.refreshLaunches()

        upcoming_launches_recycler.layoutManager = LinearLayoutManager(context)
        upcoming_launches_recycler.adapter = recyclerUpcomingLaunchesAdapter

        upcoming_launches_refresh.setOnRefreshListener {
            loading_launch.visibility = View.VISIBLE
            not_found_launch.visibility = View.INVISIBLE
            upcoming_launches_recycler.visibility = View.INVISIBLE
            viewModel.refreshLaunches()
            upcoming_launches_refresh.isRefreshing = false
        }

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.launches.observe(viewLifecycleOwner, { currentLaunches ->
            currentLaunches?.let {
                upcoming_launches_recycler.visibility = View.VISIBLE
                recyclerUpcomingLaunchesAdapter.updateLaunchList(currentLaunches)
            }
        })

        viewModel.launchNotFound.observe(viewLifecycleOwner, { isNotFound ->
            isNotFound?.let {
                if (it){
                    not_found_launch.visibility = View.VISIBLE
                    upcoming_launches_recycler.visibility = View.INVISIBLE
                } else{
                    not_found_launch.visibility = View.INVISIBLE
                }
            }
        })

        viewModel.launchesLoading.observe(viewLifecycleOwner, { isLoading ->
            isLoading?.let {
                if (it){
                    loading_launch.visibility = View.VISIBLE
                    not_found_launch.visibility = View.INVISIBLE
                    upcoming_launches_recycler.visibility = View.INVISIBLE
                } else{
                    loading_launch.visibility = View.INVISIBLE
                }
            }

        })
    }

    fun onDetailClick(data: UpcomingModel) {
        val intent = Intent(context, UpcomingDetail::class.java)
        intent.putExtra("Upcoming", data)
        context?.let { ContextCompat.startActivity(it, intent, null) }
    }

}