package com.ajijul.ahoytestdata.ui.main.forecast

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ajijul.ahoytestdata.base.BaseViewModel
import com.ajijul.ahoytestdata.store.DataStoreRepository
import com.ajijul.ahoytestdata.store.LAST_CURRENT_LOCATION_DATA
import com.ajijul.ahoytestdata.store.LAST_CURRENT_LOCATION_FORECAST_DATA
import com.ajijul.ahoytestdata.utils.ScreenState
import com.ajijul.network.data.forecast.ForecastBaseModel
import com.ajijul.network.data.forecast.ThreeHoursModel
import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ForecastViewModel @Inject constructor(
    var forecastRepo: ForecastRepository,
    var dataStoreRepository: DataStoreRepository,
    var gson: Gson
) :
    BaseViewModel() {

    private var groups = MutableLiveData<Map<String, List<ThreeHoursModel>>>()
    private var forecast = MutableLiveData<ResultWrapper<ForecastBaseModel>>()
    val TAG = "Forecast ViewModel"
    private val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var outFormat = SimpleDateFormat("EEE", Locale.getDefault())


    init {
        Log.d(TAG, "Forecast ViewModel")
    }

    fun observeForecast(
        lat: String,
        lon: String,
        apiKey: String,
        giveMeRemote: Boolean
    ): LiveData<ResultWrapper<ForecastBaseModel>> {

        viewModelScope.launch {
            screenState.value = ScreenState.LOADING
            val localData = dataStoreRepository.getString(LAST_CURRENT_LOCATION_FORECAST_DATA)
            val result = if (giveMeRemote) forecastRepo.getForecastOfMyCurrentLocation(lat, lon, apiKey)
            else if (localData == null) ResultWrapper.NetworkError
            else ResultWrapper.Success(gson.fromJson(localData, ForecastBaseModel::class.java))
            screenState.postValue(
                when (result) {
                    is ResultWrapper.Success -> {
                        dataStoreRepository.putString(
                            LAST_CURRENT_LOCATION_FORECAST_DATA,
                            gson.toJson(result.value)
                        )
                        forecast.postValue(result)
                        startGrouping(result)
                        ScreenState.RENDER
                    }
                    else -> ScreenState.ERROR
                }
            )
        }
        return forecast
    }

    private fun startGrouping(result: ResultWrapper<ForecastBaseModel>) {
        if (result !is ResultWrapper.Success<ForecastBaseModel>)
            return
        result.let {
            groups.postValue(it.value.list.groupBy { item ->
                val date = format.parse(item.dt_txt)
                outFormat.format(date ?: return);
            })
        }
    }

    fun observeScreenState(): LiveData<ScreenState> {
        return screenState
    }


    fun observeGroupData(): LiveData<Map<String, List<ThreeHoursModel>>> {
        return groups
    }

    fun getForecastResult(): LiveData<ResultWrapper<ForecastBaseModel>> {
        return forecast
    }

}

