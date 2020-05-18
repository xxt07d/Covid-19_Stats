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
        var responseReceived: Boolean = false
        var countryListCall: Call<List<Country>> = covidApiService.getCountries()
        var countries: List<Country>? = null
        countryListCall.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if(!response.isSuccessful) {
                    println(response.code())
                    return
                }
                countries = response.body()
                responseReceived = true
                for (country in countries.orEmpty()) {
                    println(country.toString())
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                println(t.message)
                responseReceived = true
            }
        })
        while(!responseReceived) {
            Thread.sleep(100)
        }
        println("size of countries: ${countries?.size}")
        countries?.forEach {
            if (it.Country.equals("Hungary")) {
                println("data of Hungary: ${it.Country}, ${it.ISO2}, ${it.Slug}")
            }
        }
    }

    @Test
    fun testGetDailies() {
        var responseReceived: Boolean = false
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
                responseReceived = true
                for (daily in dailies.orEmpty()){
                    System.out.println(daily.toString())
                }
            }

            override fun onFailure(call: Call<List<Daily>>, t: Throwable) {
                println(t.message)
                responseReceived = true
            }
        })
        while(!responseReceived) {
            Thread.sleep(100)
        }
    }
}