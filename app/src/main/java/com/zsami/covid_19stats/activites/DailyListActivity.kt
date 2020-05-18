package com.zsami.covid_19stats.activites

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.adapters.DailyRecyclerViewAdapter
import com.zsami.covid_19stats.adapters.DailyRecyclerViewAdapter.*
import com.zsami.covid_19stats.data.AppDatabase
import com.zsami.covid_19stats.data.Daily
import com.zsami.covid_19stats.interactors.NetworkInteractor
import com.zsami.covid_19stats.network.Country
import com.zsami.covid_19stats.presenters.DailyListPresenter
import com.zsami.covid_19stats.screens.DailyListScreen
import java.lang.Exception
import java.util.Date

const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class DailyListActivity : AppCompatActivity(), DailyListScreen {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DailyRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var networkInteractor: NetworkInteractor = NetworkInteractor.getInstance()
    private lateinit var database: AppDatabase

    private lateinit var startCountryName: String
    private lateinit var countryName: String

    private var networkDailies: List<com.zsami.covid_19stats.network.Daily>? = arrayListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_list_activity)
        database = AppDatabase.getInstance(this.baseContext)
        startCountryName = "Bulgaria"
        countryName = "Hungary"


        try{
            Thread(Runnable {
                dailyListGetDailyData()
            }).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        configureRecyclerView()

    }

    override fun onStart() {
        super.onStart()
        DailyListPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        DailyListPresenter.detachScreen()
    }


    override fun dailyListGetDailyData(){
        if ( networkInteractor == null) {
            networkInteractor = NetworkInteractor()
        }
        if (database == null) {
            database = Room.inMemoryDatabaseBuilder(this.baseContext,
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }
        if (startCountryName != countryName) {
            startCountryName = countryName
        }
        networkDailies = networkInteractor.getDailies(countryName.toLowerCase())

        var databaseDailies = arrayListOf<Daily>()
        networkDailies?.forEach{
            val daily: Daily = Daily(
                dayId = null,
                date = it.Date.substring(0,10),
                country = it.Country,
                infections = it.Confirmed.toLong(),
                deaths =  it.Deaths.toLong(),
                recoveries = it.Recovered.toLong(),
                actives = it.Active.toLong()
            )
            databaseDailies.add(daily)
        }

        if (databaseDailies.size > 0) {
            database.clearAllTables()
            database.dailyDao().insertDailies()
        }

        runOnUiThread {
            val reversedList = networkDailies!!.reversed()
            viewAdapter = DailyRecyclerViewAdapter(reversedList)
            viewAdapter.setOnItemClickListener(
                object : OnItemClickListener {
                    override fun onItemclick(position: Int) {
                        var extraMessageList = arrayOf(
                            reversedList[position].Confirmed.toString(),
                            reversedList[position].Active.toString(),
                            reversedList[position].Deaths.toString(),
                            reversedList[position].Recovered.toString()
                        )
                        val intent = Intent(applicationContext, DetailedActivity::class.java).apply {
                            putExtra(EXTRA_MESSAGE, extraMessageList)
                        }
                        startActivity(intent)

                    }
                }
            )
            recyclerView.adapter = viewAdapter
            viewAdapter.notifyDataSetChanged()
        }
    }

    override fun dailyListGetCountries() {
        if ( networkInteractor == null) {
            networkInteractor = NetworkInteractor()
        }
        if (database == null) {
            database = Room.inMemoryDatabaseBuilder(this.baseContext,
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        }
        var countries: List<Country>? = networkInteractor.getCountries()
        if (countries != null) {
            database.clearAllTables()
            database.dailyDao().insertDailies()
        }

    }

    override fun foo() {
        TODO("Not yet implemented")
    }

    fun configureRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = DailyRecyclerViewAdapter(networkDailies!!)

        recyclerView = findViewById<RecyclerView>(R.id.dailyRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
}