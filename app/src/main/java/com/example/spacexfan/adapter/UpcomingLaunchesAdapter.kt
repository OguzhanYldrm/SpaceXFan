package com.example.spacexfan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexfan.R
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.view.detail.RocketDetail
import com.example.spacexfan.view.detail.UpcomingDetail
import com.example.spacexfan.viewmodel.UpcomingLaunchesViewModel
import kotlinx.android.synthetic.main.activity_upcoming_detail.view.*
import kotlinx.android.synthetic.main.item_rocket_list.view.*
import kotlinx.android.synthetic.main.item_rocket_list.view.go_detail_page
import kotlinx.android.synthetic.main.item_upcoming_launches.view.*

class UpcomingLaunchesAdapter(val launchesList : ArrayList<UpcomingModel>) : RecyclerView.Adapter<UpcomingLaunchesAdapter.UpcomingLaunchesViewHolder>() {
    class UpcomingLaunchesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingLaunchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_upcoming_launches, parent,false)
        return UpcomingLaunchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingLaunchesViewHolder, position: Int) {

        //Flight Name
        holder.itemView.title_launch.text = launchesList[position].name

        //Detail
        holder.itemView.details.text = launchesList[position].details

        //Flight Date
        holder.itemView.date_launch.text = launchesList[position].fireDate



        //Detail Button
        holder.itemView.go_detail_page.setOnClickListener{
            val intent = Intent(holder.itemView.context, UpcomingDetail::class.java)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return launchesList.size
    }

    fun updateLaunchList(newLaunchesList : List<UpcomingModel>){
        launchesList.clear()
        launchesList.addAll(newLaunchesList)
        notifyDataSetChanged()
    }

}