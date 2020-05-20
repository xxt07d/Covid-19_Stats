package com.zsami.covid_19stats.activites


import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.firebase.analytics.FirebaseAnalytics
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.adapters.DailyRecyclerViewAdapter
import com.zsami.covid_19stats.adapters.DailyRecyclerViewAdapter.OnItemClickListener
import com.zsami.covid_19stats.data.AppDatabase
import com.zsami.covid_19stats.data.Daily
import com.zsami.covid_19stats.interactors.NetworkInteractor
import com.zsami.covid_19stats.network.Country
import com.zsami.covid_19stats.presenters.DailyListPresenter
import com.zsami.covid_19stats.screens.DailyListScreen


const val EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE"

class DailyListActivity : AppCompatActivity(), DailyListScreen {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: DailyRecyclerViewAdapter
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var networkInteractor: NetworkInteractor = NetworkInteractor.getInstance()
    private lateinit var database: AppDatabase

    private lateinit var menu: Menu

    private var networkDailies: List<com.zsami.covid_19stats.network.Daily>? = arrayListOf()
    private var databaseDailies: List<Daily>? = arrayListOf()
    private var networkCountries: List<String>? = arrayListOf()

    //analytics
    private var mFirebaseAnalytics: FirebaseAnalytics? = null

    companion object {
        private var startCountryName: String = "Hungary"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_list_activity)
        database = AppDatabase.getInstance(this.baseContext)

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this)


        try{
            Thread(Runnable {
                dailyListGetDailyData()
                dailyListGetCountries()
            }).start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        configureRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu!!
        if(networkCountries!!.isNotEmpty()) {
            menuInflater.inflate(R.menu.daily_list_menu, menu)
            val item = menu!!.findItem(R.id.spinner)
            var spinner = item.actionView as Spinner


            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                applicationContext,
                R.layout.spinner_item,
                networkCountries!!
            )
            adapter.setDropDownViewResource(R.layout.spinner_item)

            spinner.adapter = adapter
            spinner.setSelection(networkCountries!!.indexOf(startCountryName))
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    //do nothing
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    startCountryName = networkCountries!![spinner.selectedItemPosition]
                    if(startCountryName == "Albania"){
                        throw NullPointerException()
                    }
                    try{
                        Thread(Runnable {
                            dailyListGetDailyData()
                        }).start()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        }
        return true
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
        networkDailies = networkInteractor.getDailies(startCountryName.toLowerCase())

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
        var reversedList: List<Daily> = arrayListOf()
        if(networkDailies.isNullOrEmpty()) {
            reversedList = database.dailyDao().getAllDailies().reversed()
        }
        runOnUiThread {
            if(!networkDailies.isNullOrEmpty()) {
                 reversedList = databaseDailies!!.reversed()
            }
            viewAdapter = DailyRecyclerViewAdapter(reversedList)
            viewAdapter.setOnItemClickListener(
                object : OnItemClickListener {
                    override fun onItemclick(position: Int) {
                        var extraMessageList = arrayOf(
                            reversedList[position].infections.toString(),
                            reversedList[position].actives.toString(),
                            reversedList[position].deaths.toString(),
                            reversedList[position].recoveries.toString()
                        )
                        if (reversedList[position].date == "2020-05-13") {
                            throw NullPointerException()
                        }
                        val bundle = Bundle()
                        bundle.putString("day",reversedList[position].date)
                        mFirebaseAnalytics?.logEvent("day_opened", bundle)

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
        var countries = networkInteractor.getCountries()
        var countryNameList = arrayListOf<String>()
        countries?.forEach{
            countryNameList.add(it.Country)
        }
        countryNameList.sort()
        networkCountries = countryNameList
        runOnUiThread {
            onCreateOptionsMenu(menu)
        }
    }

    override fun foo() {
        TODO("Not yet implemented")
    }

    fun configureRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = DailyRecyclerViewAdapter(databaseDailies!!.reversed())

        recyclerView = findViewById<RecyclerView>(R.id.dailyRecyclerView).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
}