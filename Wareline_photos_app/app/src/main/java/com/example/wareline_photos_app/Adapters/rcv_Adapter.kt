package com.example.wareline_photos_app.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.wareline_photos_app.R
import com.example.wareline_photos_app.custom_Image_Loader.CustomImageLoader
import com.example.wareline_photos_app.dataClass.photo_data

class rcv_Adapter(private val context: Context,private val dataSet: List<photo_data>)
    : RecyclerView.Adapter<rcv_Adapter.ViewHolder>() {


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val img1 : ImageView = view.findViewById(R.id.img1_rcv)
        val img2 : ImageView = view.findViewById(R.id.img2_rcv)
        val img3 : ImageView = view.findViewById(R.id.img3_rcv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): rcv_Adapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_item_rcv,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size/3
    }

    override fun onBindViewHolder(holder: rcv_Adapter.ViewHolder, position: Int) {
        val imgload = CustomImageLoader(context)
        val startIndex = position * 3

        if (startIndex < dataSet.size) {
            imgload.loadImage(dataSet[startIndex].src.small, holder.img1)
        }

        if (startIndex + 1 < dataSet.size) {
            imgload.loadImage(dataSet[startIndex + 1].src.small, holder.img2)
        }

        if (startIndex + 2 < dataSet.size) {
            imgload.loadImage(dataSet[startIndex + 2].src.small, holder.img3)
        }

    }

}