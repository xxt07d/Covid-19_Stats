package com.zsami.covid_19stats.orm

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.zsami.covid_19stats.data.AppDatabase
import org.junit.Before
import org.junit.Test

class DailyDaoTest {

    var database: AppDatabase? = null

    @Before
    fun initDB() {
        /*database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()*/
    }

    @Test
    fun testDao() {

    }

    fun closeDb() {
        database?.close()
    }
}