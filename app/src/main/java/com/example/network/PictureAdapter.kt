package com.example.network

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.network.databinding.PictureItemBinding
import com.example.network.model.Picture
import com.squareup.picasso.Picasso

class PictureAdapter(private val pictureList: List<Picture>): RecyclerView.Adapter<PictureAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(TAG, "onCreateViewHolder() called with: parent = $parent, viewType = $viewType")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.picture_item, parent, false)  // step 2 -- inflate

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(pictureList[position])
    }

    override fun getItemCount() = pictureList.size

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        private val pictureItemBinding = PictureItemBinding.bind(view)  // step 1 -- bind

        fun bind(pictureItem: Picture) = with (pictureItemBinding) {
            Log.d(TAG, "bind() called ${pictureItem.title}")
            headerTv.text = pictureItem.title

            Picasso.get().load(pictureItem.url)
                .into(img1)
            Picasso.get().load(pictureItem.thumbnailUrl)
                .into(img2)
        }
    }

    companion object {
        const val TAG = "PicAdaptLog"
    }
}