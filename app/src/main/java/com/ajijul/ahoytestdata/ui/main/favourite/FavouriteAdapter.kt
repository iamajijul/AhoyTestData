package com.ajijul.ahoytestdata.ui.main.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ajijul.ahoytestdata.databinding.FavouriteListItemBinding
import kotlin.collections.ArrayList

class FavouriteAdapter(var favList: ArrayList<String>, var event: (String) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FavouriteViewHolder(
            FavouriteListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), event
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val favHolder = holder as FavouriteViewHolder
        favHolder.setData(favList[position])
    }

    override fun getItemCount(): Int {
        return favList.size
    }

    fun informMe(it: ArrayList<String>) {
        favList = it
        notifyDataSetChanged()
    }
}