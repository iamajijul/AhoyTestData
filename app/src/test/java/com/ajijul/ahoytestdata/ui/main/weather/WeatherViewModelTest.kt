@file:OptIn(ExperimentalCoroutinesApi::class)

package com.ajijul.ahoytestdata.ui.main.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ajijul.network.data.weather.WeatherBaseModel
import com.ajijul.network.utils.ResultWrapper
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class WeatherViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val dispatcher = TestCoroutineDispatcher()
    lateinit var weatherViewModel: WeatherViewModel
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    @Before
    fun init() {
        weatherViewModel =
            WeatherViewModel(FakeWeatherRepository(), FakeDataStoreRepository(), gson)
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
    }

    @Test
    fun `get success in case of correct remote fetch`() {
        val mockedResponse = MockResponseFileReader("dubai_weather.json").content
        weatherViewModel.fetchWeather("Dubai", "", true)
        Assert.assertEquals(
            ResultWrapper.Success(
                gson.fromJson(
                    mockedResponse,
                    WeatherBaseModel::class.java
                )
            ), weatherViewModel.getWeatherObserver().value
        )
    }
}