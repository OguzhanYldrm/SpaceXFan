package com.example.spacexfan.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.spacexfan.R
import com.example.spacexfan.viewmodel.UpcomingDetailViewModel
import kotlinx.android.synthetic.main.activity_upcoming_detail.*

class UpcomingDetail : AppCompatActivity() {

    private lateinit var viewModel: UpcomingDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_detail)

        viewModel = ViewModelProviders.of(this).get(UpcomingDetailViewModel::class.java)
        viewModel.getData()

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.upcomingDetailLiveData.observe(this, { upcomingDetail ->
            upcomingDetail?.let {
                upcoming_date.text = it.fireDate
                upcoming_details.text = it.details
                upcoming_flight_name.text = it.name
                upcoming_flight_number.text = it.flightNumber.toString()
                //upcoming_image.text = it
                upcoming_rocket.text = it.rocket

            }
        })
    }
}
