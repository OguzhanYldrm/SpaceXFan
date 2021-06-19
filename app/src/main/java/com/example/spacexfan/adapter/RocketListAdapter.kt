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
import com.example.spacexfan.utils.loadImage
import com.example.spacexfan.utils.notFoundPlaceholder
import com.example.spacexfan.view.tabs.RocketListFragment
import kotlinx.android.synthetic.main.item_rocket_list.view.*


class RocketListAdapter(rockets: ArrayList<RocketModel>, parentFragment : RocketListFragment) : RecyclerView.Adapter<RocketListAdapter.RocketListViewHolder>() {
    private val rocketList : ArrayList<RocketModel> = rockets
    private val fragment : RocketListFragment = parentFragment

    class RocketListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rocket_list, parent, false)
        return RocketListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RocketListViewHolder, position: Int) {

        //Rocket Name
        holder.itemView.title.text = rocketList[position].name

        //First Flight Date
        val firstFlight : String = "First Flight : " + rocketList[position].firstFlight
        holder.itemView.date.text = firstFlight

        //Description
        var description : String? = rocketList[position].description
        if (description != null){
            if(description.length>120){
                description = description.substring(0, 119) + "..."
            }
        } else {
            description = "No description found for this rocket. Please check links."
        }

        holder.itemView.supporting_text.text = description

        //Company Name
        holder.itemView.company.text = rocketList[position].company

        //Image
        val imageList : List<String>  = rocketList[position].flickrImages
        if (imageList.size > 0){
            holder.itemView.media_image.loadImage(
                imageList[0],
                notFoundPlaceholder(holder.itemView.context)
            )
        }

        //Wikipedia
        val wikiLink : String = rocketList[position].wikipedia

        if (wikiLink != ""){
            holder.itemView.go_wikipedia.setOnClickListener {
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(wikiLink))
                startActivity(holder.itemView.context, browserIntent, null)
            }
        }


        //Detail Button
        val data: RocketModel = rocketList[position]
        holder.itemView.go_detail_page.setOnClickListener{
            fragment.onDetailClick(data)
        }


    }

    override fun getItemCount(): Int {
        return rocketList.size
    }

    fun updateRocketList(newRocketList: List<RocketModel>){
        rocketList.clear()
        rocketList.addAll(newRocketList)
        notifyDataSetChanged()
    }
}