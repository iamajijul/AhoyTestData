package com.ajijul.ahoytestdata.ui.main.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ajijul.ahoytestdata.base.BaseViewModel
import com.ajijul.ahoytestdata.utils.ScreenState
import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(var weatherRepo: WeatherRepository) :
    BaseViewModel() {

    private var weather = MutableLiveData<ResultWrapper<WeatherBaseModel>>()
    val TAG = "Weather ViewModel"


    init {
        Log.d(TAG, "Weather ViewModel")
    }

    fun getWeatherObserver(): LiveData<ResultWrapper<WeatherBaseModel>>{
        return weather
    }

    fun observeWeather(
        cityName: String,
        apiKey: String
    ): LiveData<ResultWrapper<WeatherBaseModel>> {

        if (weather.value != null && weather.value is ResultWrapper.Success<WeatherBaseModel>
            && (weather.value as ResultWrapper.Success<WeatherBaseModel>).value.name == cityName
        )
            return weather

        viewModelScope.launch {
            screenState.value = ScreenState.LOADING
            val result = weatherRepo.getWeatherOfParticularCity(cityName, apiKey)
            weather.postValue(result)
            val newState = if (result == null) ScreenState.ERROR else ScreenState.RENDER
            screenState.postValue(newState)

        }

        return weather
    }

    fun observeScreenState(): LiveData<ScreenState> {
        return screenState
    }


}