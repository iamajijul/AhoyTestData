package com.ajijul.ahoytestdata.ui.main.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ajijul.ahoytestdata.base.BaseViewModel
import com.ajijul.ahoytestdata.store.DataStoreRepository
import com.ajijul.ahoytestdata.store.FAVORITE_LIST
import com.ajijul.ahoytestdata.store.LAST_CURRENT_LOCATION_DATA
import com.ajijul.ahoytestdata.utils.ScreenState
import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavourViewModel @Inject constructor(
    var dataStoreRepository: DataStoreRepository,
) :
    BaseViewModel() {
    private var favouriteList: MutableLiveData<ArrayList<String>> = MutableLiveData()
    var _favouriteList: LiveData<ArrayList<String>> = favouriteList

    val TAG = "Favourite ViewModel"

    init {
        getFavouriteList()
        Log.d(TAG, "Favourite ViewModel")
    }

    private fun getFavouriteList() {
        viewModelScope.launch {
            favouriteList.value = dataStoreRepository.getStringArray(FAVORITE_LIST)
        }
    }


    private fun modifyFavouriteList(favList: ArrayList<String>) {
        viewModelScope.launch {
            dataStoreRepository.putStringStringArray(FAVORITE_LIST, favList)
        }
    }

    fun addToFavouriteList(cityName: String) {
        if (isMyFavouriteCity(cityName))
            return
        favouriteList.value?.add(cityName)
        favouriteList.value?.let { modifyFavouriteList(it) }
    }

    fun removeToFavouriteList(cityName: String) {
        favouriteList.value?.remove(cityName)
        favouriteList.value?.let { modifyFavouriteList(it) }
    }

    fun isMyFavouriteCity(cityName: String): Boolean =
        favouriteList.value?.contains(cityName) ?: false
}