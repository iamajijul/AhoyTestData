package com.ajijul.ahoytestdata.ui.main.favourite

import androidx.recyclerview.widget.RecyclerView
import com.ajijul.ahoytestdata.databinding.FavouriteListItemBinding
import kotlinx.android.synthetic.main.favourite_list_item.view.*

class FavouriteViewHolder(itemView : FavouriteListItemBinding) : RecyclerView.ViewHolder(itemView.root) {
    fun setData(cityName : String){
        itemView.appCompatTextViewCityName.text = cityName
    }
}