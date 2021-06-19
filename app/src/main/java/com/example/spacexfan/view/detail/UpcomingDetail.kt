package com.example.spacexfan.view.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.spacexfan.R
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.utils.loadImage
import com.example.spacexfan.utils.notFoundPlaceholder
import com.example.spacexfan.viewmodel.UpcomingDetailViewModel
import kotlinx.android.synthetic.main.activity_upcoming_detail.*
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem


class UpcomingDetail : AppCompatActivity() {

    private lateinit var viewModel: UpcomingDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upcoming_detail)

        val intent = intent
        val upcomingData: UpcomingModel = intent.getSerializableExtra("Upcoming") as UpcomingModel

        viewModel = ViewModelProviders.of(this).get(UpcomingDetailViewModel::class.java)
        viewModel.getData(upcomingData)

        observeLiveData()

    }

    fun observeLiveData(){
        viewModel.upcomingDetailLiveData.observe(this, { upcomingDetail ->
            upcomingDetail?.let {
                upcoming_date.text = it.fireDate
                upcoming_details.text = it.details
                upcoming_flight_name.text = it.name
                upcoming_flight_number.text = it.flightNumber.toString()
                if (it.links.patch.small != null){
                    upcoming_image.loadImage(it.links.patch.small, notFoundPlaceholder(applicationContext))
                }

                upcoming_rocket.text = it.rocket

            }
        })
    }
}
