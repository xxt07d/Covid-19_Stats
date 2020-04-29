package com.zsami.covid_19stats.network

import org.junit.Before
import org.junit.Test
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class CovidApiTest {

    @Inject
    lateinit var covidApiService: CovidApiService

    @Before
    fun setup() {
        covidApiService = CovidApiService.create()
    }

    @Test
    fun testGetCountries() {
        var countryListCall: Call<List<Country>> = covidApiService.getCountries()
        var countries: List<Country>? = null
        countryListCall.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if(!response.isSuccessful) {
                    println(response.code())
                    return
                }
                countries = response.body()
                for (country in countries.orEmpty()) {
                    println(country.toString())
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                println(t.message)
            }
        })
    }

    @Test
    fun testGetDailies() {
        val country: String = "hungary"
        var dailyListCall: Call<List<Daily>> = covidApiService.getDailies(country)
        var dailies: List<Daily>? = null
        dailyListCall.enqueue(object: Callback<List<Daily>> {
            override fun onResponse(call: Call<List<Daily>>, response: Response<List<Daily>>) {
                if(!response.isSuccessful) {
                    println(response.code())
                    return
                }
                dailies = response.body()
                for (daily in dailies.orEmpty()){
                    System.out.println(daily.toString())
                }
            }

            override fun onFailure(call: Call<List<Daily>>, t: Throwable) {
                println(t.message)
            }
        })
    }
}