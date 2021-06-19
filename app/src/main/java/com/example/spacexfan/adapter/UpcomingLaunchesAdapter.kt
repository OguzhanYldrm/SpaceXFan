package com.example.spacexfan.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexfan.R
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.model.UpcomingModel
import com.example.spacexfan.utils.loadImage
import com.example.spacexfan.utils.notFoundPlaceholder
import com.example.spacexfan.view.detail.RocketDetail
import com.example.spacexfan.view.detail.UpcomingDetail
import com.example.spacexfan.view.tabs.RocketListFragment
import com.example.spacexfan.view.tabs.UpcomingLaunchesFragment
import com.example.spacexfan.viewmodel.UpcomingLaunchesViewModel
import kotlinx.android.synthetic.main.activity_upcoming_detail.view.*
import kotlinx.android.synthetic.main.item_rocket_list.view.*
import kotlinx.android.synthetic.main.item_rocket_list.view.go_detail_page
import kotlinx.android.synthetic.main.item_upcoming_launches.view.*

class UpcomingLaunchesAdapter(launches : ArrayList<UpcomingModel>, parentFragment: UpcomingLaunchesFragment) : RecyclerView.Adapter<UpcomingLaunchesAdapter.UpcomingLaunchesViewHolder>() {
    private val launchesList : ArrayList<UpcomingModel> = launches
    private val fragment : UpcomingLaunchesFragment = parentFragment

    class UpcomingLaunchesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingLaunchesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_upcoming_launches, parent,false)
        return UpcomingLaunchesViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingLaunchesViewHolder, position: Int) {

        //Flight Name
        holder.itemView.title_launch.text = launchesList[position].name

        //Detail
        var detail : String? = launchesList[position].details
        if (detail != null){
            if(detail.length>120){
                detail = detail.substring(0, 119) + "..."
            }
        } else{
            detail = "No detailed information found. Please check the links."
        }

        holder.itemView.details.text = detail


        //Flight Date
        holder.itemView.date_launch.text = launchesList[position].fireDate

        //Image
        val url : String?  = launchesList[position].links.patch.small
        if (url != null){
            if (url != "" && (url.contains("http") || url.contains("https"))){
                holder.itemView.media_image_launch.loadImage(
                    url,
                    notFoundPlaceholder(holder.itemView.context)
                )
            }
        }

        //Wikipedia
        val wikiLink : String? = launchesList[position].links.wikipedia

        if (wikiLink != null){
            if (wikiLink != ""){
                holder.itemView.go_wikipedia_launch.setOnClickListener {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiLink))
                    startActivity(holder.itemView.context, browserIntent, null)
                }
            }
        }


        //Detail Button
        val data : UpcomingModel = launchesList[position]
        holder.itemView.go_detail_page_launch.setOnClickListener{
        fragment.onDetailClick(data)
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