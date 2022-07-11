package com.ajijul.ahoytestdata.ui.main.weather

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
class WeatherViewModel @Inject constructor(
    var weatherRepo: WeatherRepository,
    var dataStoreRepository: DataStoreRepository,
    var gson: Gson
) :
    BaseViewModel() {
    private var weather = MutableLiveData<ResultWrapper<WeatherBaseModel>>()
    val TAG = "Weather ViewModel"


    init {
        Log.d(TAG, "Weather ViewModel")
    }

    fun setFavouriteList(favList : HashSet<String>) {
        viewModelScope.launch {
           dataStoreRepository.putStringStringArray(FAVORITE_LIST,favList)
        }
    }

    fun getWeatherObserver(): LiveData<ResultWrapper<WeatherBaseModel>> {
        return weather
    }

    fun observeWeather(
        cityName: String,
        apiKey: String,
        giveMeRemote: Boolean
    ): LiveData<ResultWrapper<WeatherBaseModel>> {

        if (weather.value != null && weather.value is ResultWrapper.Success<WeatherBaseModel>
            && (weather.value as ResultWrapper.Success<WeatherBaseModel>).value.name == cityName
        ) return weather
        viewModelScope.launch {
            screenState.value = ScreenState.LOADING
            val localData = dataStoreRepository.getString(LAST_CURRENT_LOCATION_DATA)
            val result = if (giveMeRemote) weatherRepo.getWeatherOfParticularCity(cityName, apiKey)
            else if (localData == null) ResultWrapper.NetworkError
            else ResultWrapper.Success(gson.fromJson(localData, WeatherBaseModel::class.java))
            screenState.postValue(
                when (result) {
                    is ResultWrapper.Success -> {
                        dataStoreRepository.putString(
                            LAST_CURRENT_LOCATION_DATA,
                            gson.toJson(result.value)
                        )
                        weather.postValue(result)
                        ScreenState.RENDER
                    }
                    else -> ScreenState.ERROR
                }
            )
        }

        return weather
    }

    fun observeScreenState(): LiveData<ScreenState> {
        return screenState
    }

    suspend fun getFavouriteList(): HashSet<String> {
        return withContext(viewModelScope.coroutineContext) {
            dataStoreRepository.getStringArray(FAVORITE_LIST)
        }
    }


}