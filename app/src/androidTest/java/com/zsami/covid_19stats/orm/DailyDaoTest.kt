package com.zsami.covid_19stats.orm

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.zsami.covid_19stats.data.AppDatabase
import com.zsami.covid_19stats.data.Daily
import org.junit.After
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class DailyDaoTest {

    @Inject
    var database: AppDatabase? = null

    @Before
    fun initDB() {
        database = AppDatabase.getInstance(InstrumentationRegistry.getInstrumentation().targetContext)
    }

    @Test
    fun testDao() {
        val daily1: Daily = Daily(
            dayId = null,
            date = "2020-05-12",
            country = "Hungary",
            infections = 3000,
            deaths =  300,
            recoveries = 1500,
            actives = 1200
        )
        val daily2: Daily = Daily(
            dayId = null,
            date = "2020-05-13",
            country = "Hungary",
            infections = 3030,
            deaths =  310,
            recoveries = 1510,
            actives = 1210
        )
        database?.dailyDao()?.insertDailies(daily1, daily2)
        val results = database?.dailyDao()?.getAllDailies()
        assert(daily1 == results?.get(0))
        assert(daily2 == results?.get(1))
        database?.clearAllTables()
    }

    @After
    fun closeDb() {
        database?.close()
    }
}