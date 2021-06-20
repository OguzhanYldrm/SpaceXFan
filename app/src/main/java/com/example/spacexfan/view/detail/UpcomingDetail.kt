package com.example.spacexfan.view.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.spacexfan.R
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.utils.loadImage
import com.example.spacexfan.utils.notFoundPlaceholder
import com.example.spacexfan.viewmodel.UpcomingDetailViewModel
import kotlinx.android.synthetic.main.activity_rocket_detail.*
import kotlinx.android.synthetic.main.activity_upcoming_detail.*
import kotlinx.android.synthetic.main.item_upcoming_launches.view.*
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

    fun observeLiveData() {
        viewModel.upcomingDetailLiveData.observe(this, { upcomingDetail ->
            upcomingDetail?.let {
                //Flight Date
                val strs = it.fireDate.split("T").toTypedArray()
                val fireDate: String = strs[0]
                upcoming_date.text = fireDate
                upcoming_details.text = it.details
                upcoming_flight_name.text = it.name
                upcoming_flight_number.text = it.flightNumber.toString()
                upcoming_image.loadImage(
                    it.links.patch.small,
                    notFoundPlaceholder(applicationContext)
                )

                //wiki
                go_wiki_upcoming.setOnClickListener {
                    if (upcomingDetail.links.wikipedia != null) {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(upcomingDetail.links.wikipedia))
                        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        ContextCompat.startActivity(applicationContext, browserIntent, null)
                    } else {
                        Toast.makeText(applicationContext, "No Link Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                //reddit
                go_reddit_upcoming.setOnClickListener {
                    if (upcomingDetail.links.reddit.campaign != null) {
                        val browserIntent =
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(upcomingDetail.links.reddit.campaign)
                            )
                        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        ContextCompat.startActivity(applicationContext, browserIntent, null)
                    } else {
                        Toast.makeText(applicationContext, "No Link Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


                //article
                go_article_upcoming.setOnClickListener {
                    if (upcomingDetail.links.article != null) {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(upcomingDetail.links.article))
                        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        ContextCompat.startActivity(applicationContext, browserIntent, null)
                    } else {
                        Toast.makeText(applicationContext, "No Link Found", Toast.LENGTH_SHORT)
                            .show()
                    }
                }


            }
        })
    }
}
