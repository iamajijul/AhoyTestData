package com.ajijul.ahoytestdata.di

import com.ajijul.ahoytestdata.ui.main.forecast.ForecastRepoImpl
import com.ajijul.ahoytestdata.ui.main.forecast.ForecastRepository
import com.ajijul.ahoytestdata.ui.main.weather.WeatherRepoImpl
import com.ajijul.ahoytestdata.ui.main.weather.WeatherRepository
import com.ajijul.ahoytestdata.utils.MessageHandler
import com.ajijul.ahoytestdata.utils.MessageHandlerImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AbstractionModule {
    @Binds
    abstract fun provideMessageHandle(messageHandlerImp: MessageHandlerImp): MessageHandler

    @Binds
    abstract fun bindWeatherRepo(weatherRepoImpl: WeatherRepoImpl): WeatherRepository

    @Binds
    abstract fun bindForecastRepo(forecastRepoImpl: ForecastRepoImpl): ForecastRepository

}