package com.ajijul.ahoytestdata.ui.main.weather

import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper

interface WeatherRepository {
    suspend fun getWeatherOfParticularCity(cityName: String, apiKey: String):
            ResultWrapper<WeatherBaseModel>?
}