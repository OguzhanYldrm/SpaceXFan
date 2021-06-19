package com.example.spacexfan.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexfan.R
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.view.detail.RocketDetail
import kotlinx.android.synthetic.main.item_rocket_list.view.*

class RocketListAdapter(val rocketList : ArrayList<RocketModel>) : RecyclerView.Adapter<RocketListAdapter.RocketListViewHolder>() {
    class RocketListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rocket_list, parent,false)
        return RocketListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RocketListViewHolder, position: Int) {

        //Rocket Name
        holder.itemView.title.text = rocketList[position].name

        //First Flight Date
        val firstFlight : String = "First Flight : " + rocketList[position].firstFlight
        holder.itemView.date.text = firstFlight

        //Description
        var description : String = rocketList[position].description
        if(description.length>120){
            description = description.substring(0,119)
        }
        holder.itemView.supporting_text.text = description

        //Company Name
        holder.itemView.company.text = rocketList[position].company

        //Detail Button
        holder.itemView.go_detail_page.setOnClickListener{
            val intent = Intent(holder.itemView.context, RocketDetail::class.java)
            startActivity(holder.itemView.context, intent, null)
        }
    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    fun updateRocketList(newRocketList : List<RocketModel>){
        rocketList.clear()
        rocketList.addAll(newRocketList)
        notifyDataSetChanged()
    }
}