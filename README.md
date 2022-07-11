# AhoyWeatherTestData

This Application mainly use for two purpose : 
1. To check the weather of particular city in the world as user desire.
2. To chek the weather forecast for use current location

The app follows the MVVM architecture with the repository pattern, alongside Hilt for DI.

#### Module structure

There are 3 main module: app, network, store.
* app: project amin module for ui and other operation
* network: module to handle http related operation
* store: library for cache data into google data store

### Design, libraries and other stuff applied

* Data binding
* Google Material Components
* ViewModel and LiveData
* Coroutines
* Hilt for dependency injection
* Retrofit
* MVVM architecture + Repository pattern
* Junit4 
* Navigation Architecture Component
* Constraint Layout

Whole project written in kotlin language.



### Testing!

I have written two classes unit test, "BaseRepository.kt","WeatherViewModel.kt". 

** BaseRepository.kt : Present within network module
** WeatherViewModel.kt : Present within app module


### Permission!

1. Internet permission
2. Location Permission
3. Network State Permission

### Note!

"http://openweathermap.org"
not allow to search multiple city for weather, no API is there for that. so user can search only one city 
for weather. 