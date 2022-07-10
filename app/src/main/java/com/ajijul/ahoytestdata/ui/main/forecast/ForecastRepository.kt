package com.ajijul.ahoytestdata.ui.main.forecast

import com.ajijul.network.data.forecast.ForecastBaseModel
import com.ajijul.network.utils.ResultWrapper

interface ForecastRepository {
    suspend fun getForecastOfMyCurrentLocation(lat: String,lon : String, apiKey: String)
            : ResultWrapper<ForecastBaseModel>?
}