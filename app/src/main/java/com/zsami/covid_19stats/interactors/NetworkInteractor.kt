package com.zsami.covid_19stats.interactors

import com.zsami.covid_19stats.network.Country
import com.zsami.covid_19stats.network.CovidApiService
import com.zsami.covid_19stats.network.Daily
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkInteractor {
    var covidApiService: CovidApiService = CovidApiService.create()

    fun getCountries(): List<Country>? {
        var countryListCall: Call<List<Country>> = covidApiService.getCountries()
        var countries: List<Country>? = null
        countryListCall.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if(!response.isSuccessful) {
                    //itt kapok egy response kodot
                    return
                }
                countries = response.body()
                for (country in countries.orEmpty()) {

                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return countries
    }

    fun getDailies(country: String): List<Daily>? {
        var dailyListCall: Call<List<Daily>> = covidApiService.getDailies(country)
        var dailies: List<Daily>? = null
        dailyListCall.enqueue(object: Callback<List<Daily>> {
            override fun onResponse(call: Call<List<Daily>>, response: Response<List<Daily>>) {
                if(!response.isSuccessful) {
                    //itt kapok egy response kodot
                    return
                }
                dailies = response.body()
                for (daily in dailies.orEmpty()){

                }
            }

            override fun onFailure(call: Call<List<Daily>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return dailies
    }
}