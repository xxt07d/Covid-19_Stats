package com.zsami.covid_19stats.interactors

import com.zsami.covid_19stats.network.Country
import com.zsami.covid_19stats.network.CovidApiService
import com.zsami.covid_19stats.network.Daily
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkInteractor {
    var covidApiService: CovidApiService = CovidApiService.create()
    var waitRepeatAmount: Int = 0

    companion object {
        fun getInstance(): NetworkInteractor {
            return NetworkInteractor()
        }
    }

    fun getCountries(): List<Country>? {
        var responseReceived: Boolean = false
        var countryListCall: Call<List<Country>> = covidApiService.getCountries()
        var countries: List<Country>? = null
        countryListCall.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if(!response.isSuccessful) {
                    //itt kapok egy response kodot
                    responseReceived = true
                    return
                }
                countries = response.body()
                responseReceived = true
                for (country in countries.orEmpty()) {

                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {

            }
        })
        while(!responseReceived){ //&& waitRepeatAmount < 20) {
            Thread.sleep(100)
            waitRepeatAmount++
        }
        waitRepeatAmount = 0
        return countries
    }

    fun getDailies(country: String): List<Daily>? {
        var responseReceived: Boolean = false
        var dailyListCall: Call<List<Daily>> = covidApiService.getDailies(country)
        var dailies: List<Daily>? = null
        dailyListCall.enqueue(object: Callback<List<Daily>> {
            override fun onResponse(call: Call<List<Daily>>, response: Response<List<Daily>>) {
                if(!response.isSuccessful) {
                    //itt kapok egy response kodot
                    responseReceived = true
                    return
                }
                dailies = response.body()
                responseReceived = true
                for (daily in dailies.orEmpty()){

                }
            }

            override fun onFailure(call: Call<List<Daily>>, t: Throwable) {
                //responseReceived = true
            }
        })
        while(!responseReceived){ //&& waitRepeatAmount < 20) {
            Thread.sleep(100)
            waitRepeatAmount++
        }
        waitRepeatAmount = 0
        return dailies
    }
}