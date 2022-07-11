package com.ajijul.ahoytestdata.ui.main.weather

import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper
import com.google.gson.GsonBuilder

class FakeWeatherRepository : WeatherRepository{

    private val gson = GsonBuilder()
        .setLenient()
        .create()

    override suspend fun getWeatherOfParticularCity(
        cityName: String,
        apiKey: String
    ): ResultWrapper<WeatherBaseModel>? {
       val mockedResponse = MockResponseFileReader("dubai_weather.json").content
        return ResultWrapper.Success(gson.fromJson(mockedResponse,WeatherBaseModel::class.java))
    }

}