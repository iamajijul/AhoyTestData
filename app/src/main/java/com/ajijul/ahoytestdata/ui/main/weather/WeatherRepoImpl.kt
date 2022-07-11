package com.ajijul.ahoytestdata.ui.main.weather

import com.ajijul.network.apis.MainApi
import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.repos.BaseRepository
import com.ajijul.network.utils.ResultWrapper
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(private var mainApi: MainApi) : BaseRepository(),
    WeatherRepository {
    override suspend fun getWeatherOfParticularCity(
        cityName: String,
        apiKey: String
    ): ResultWrapper<WeatherBaseModel> {
        return safeApiCall { mainApi.getWeatherOfParticularCity(cityName, apiKey) }
    }
}