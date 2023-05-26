package com.test.app.LoadMaps.ApiCall

import com.test.app.LoadMaps.DataSets.PlacesSearchApi
import com.test.app.LoadMaps.DataSets.WeatherResponseApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat") lat: String, @Query("lon") lon: String, @Query("units") units: String, @Query("APPID") app_id: String): Call<WeatherResponseApi>

    @GET("geocoding/v5/mapbox.places/{searchWord}")
    fun getPlaces(@Path("searchWord") searchWord: String?,@Query("access_token") token: String): Call<PlacesSearchApi>

}