package com.zsami.covid_19stats.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApiService {

    @GET("countries")
    fun getCountries(): Call<List<Country>>

    @GET("country/{countryname}")
    fun getDailies(@Path("countryname") countryName: String): Call<List<Daily>>

    companion object {
        fun create(): CovidApiService {
            var retrofit = Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create()
                )
                .baseUrl("https://api.covid19api.com/")
                .build()

            return retrofit.create(CovidApiService::class.java)
        }
    }
}