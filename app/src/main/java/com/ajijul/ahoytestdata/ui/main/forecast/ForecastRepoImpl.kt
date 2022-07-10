package com.ajijul.ahoytestdata.ui.main.forecast

import com.ajijul.network.apis.MainApi
import com.ajijul.network.data.forecast.ForecastBaseModel
import com.ajijul.network.repos.BaseRepository
import com.ajijul.network.utils.ResultWrapper
import javax.inject.Inject

class ForecastRepoImpl @Inject constructor(var mainApi: MainApi) : BaseRepository(),
    ForecastRepository {
    override suspend fun getForecastOfMyCurrentLocation(
        lat: String,
        lon: String,
        apiKey: String
    ): ResultWrapper<ForecastBaseModel>? {
        return safeApiCall { mainApi.getForecastOfMyCurrentLocation(lat,lon, apiKey) }
    }


}