package com.zsami.covid_19stats.network

data class Daily(
    var Country: String,
    var CountryCode: String,
    var Province: String,
    var City: String,
    var CityCode: String,
    var Lat: Double,
    var Lon: Double,
    var Confirmed: Integer,
    var Deaths: Integer,
    var Recovered: Integer,
    var Active: Integer,
    var Date: String
)