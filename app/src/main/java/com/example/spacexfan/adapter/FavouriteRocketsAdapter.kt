package com.example.spacexfan.adapter

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.spacexfan.R
import com.example.spacexfan.model.RocketModel
import com.example.spacexfan.utils.loadImage
import com.example.spacexfan.utils.notFoundPlaceholder
import com.example.spacexfan.view.tabs.FavouriteRocketsFragment
import com.example.spacexfan.view.tabs.RocketListFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_rocket_detail.*
import kotlinx.android.synthetic.main.item_rocket_list.view.*


class FavouriteRocketsAdapter(rockets: ArrayList<RocketModel>, parentFragment : FavouriteRocketsFragment) : RecyclerView.Adapter<FavouriteRocketsAdapter.FavouriteRocketsViewHolder>() {
    private val rocketList : ArrayList<RocketModel> = rockets
    private val fragment : FavouriteRocketsFragment = parentFragment
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var mFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    class FavouriteRocketsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteRocketsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_rocket_list, parent, false)
        return FavouriteRocketsViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteRocketsViewHolder, position: Int) {

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
        if (imageList.isNotEmpty()){
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

        //Fav Button State
        val docIdRef =
            mFirestore.collection("Users").document(mAuth.uid.toString()).collection("Favourites")
                .document(rocketList[position].id)
        docIdRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val document = task.result
                holder.itemView.favourite_button.isSelected = document!!.exists()
            } else {
                Log.d("Firebase", "Failed with: ", task.exception)
            }
        }


        //OnclickListeners
        val data: RocketModel = rocketList[position]
        //Detail Button
        holder.itemView.go_detail_page.setOnClickListener{
            fragment.onDetailClick(data)
        }
        //Favourite Button
        holder.itemView.favourite_button.setOnClickListener {
            fragment.onFavouriteClick(data)
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