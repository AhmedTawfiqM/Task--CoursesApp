package com.atdev.courmata.pojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.atdev.courmata.R
import com.squareup.picasso.Picasso

class AdapterMedia(val context: Context, val images: List<Int>) :
    RecyclerView.Adapter<AdapterMedia.ViewHolderMedia>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMedia {
        val view =
            LayoutInflater.from(context).inflate(R.layout.single_media, parent, false)
        return ViewHolderMedia(view)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolderMedia, position: Int) {

        Picasso.get().load(images[position]).into(holder.ivMedia)
    }

    class ViewHolderMedia(itemview: View) : RecyclerView.ViewHolder(itemview) {

        var ivMedia: ImageView

        init {
            ivMedia = itemview.findViewById(R.id.ivMedia)
        }
    }
}