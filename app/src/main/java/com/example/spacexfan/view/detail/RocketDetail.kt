package com.example.spacexfan.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spacexfan.R
import com.example.spacexfan.viewmodel.RocketDetailViewModel
import kotlinx.android.synthetic.main.activity_rocket_detail.*

class RocketDetail : AppCompatActivity() {

    private lateinit var viewModel: RocketDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rocket_detail)

        viewModel = ViewModelProviders.of(this).get(RocketDetailViewModel::class.java)
        viewModel.getData()

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
}