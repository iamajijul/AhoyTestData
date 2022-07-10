package com.ajijul.network.data.forecast

data class ForecastBaseModel(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ThreeHoursModel>,
    val message: Double
)