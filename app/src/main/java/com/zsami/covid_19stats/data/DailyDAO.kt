package com.zsami.covid_19stats.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DailyDAO {
    @Query("SELECT * FROM daily")
    fun getAllDailies(): List<Daily>

    @Query("SELECT * FROM daily WHERE country = :country AND date BETWEEN :startDate AND :endDate")
    fun dailiesFromToOneCountry(startDate: Long, endDate: Long, country: String): List<Daily>

    @Insert
    fun insertDailies(vararg dailies: Daily)

    @Delete
    fun deleteDaily(daily: Daily)
}