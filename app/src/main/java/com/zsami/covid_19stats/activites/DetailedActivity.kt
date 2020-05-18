package com.zsami.covid_19stats.activites

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.presenters.DetailedPresenter
import com.zsami.covid_19stats.screens.DetailedScreen
import kotlin.random.Random

class DetailedActivity : AppCompatActivity(), DetailedScreen {

    private lateinit var registeredTextView: TextView
    private lateinit var activeTextView: TextView
    private lateinit var deathsTextView: TextView
    private lateinit var recoveredTextView: TextView
    private lateinit var motivationalTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_activity)

        supportActionBar?.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val dailyValues = intent.getStringArrayExtra(EXTRA_MESSAGE)
        registeredTextView = findViewById(R.id.registeredNumberTextView)
        activeTextView = findViewById(R.id.activeNumberTextView)
        deathsTextView = findViewById(R.id.deathsNumberTextView)
        recoveredTextView = findViewById(R.id.recoveredNumberTextView)
        motivationalTextView = findViewById(R.id.motivationalTextView)

        registeredTextView.text = dailyValues[0]
        activeTextView.text = dailyValues[1]
        deathsTextView.text = dailyValues[2]
        recoveredTextView.text = dailyValues[3]

        val motivationalStringArray = resources.getStringArray(R.array.daily_detail_positive_stuff)
        motivationalTextView.text = motivationalStringArray[Random.nextInt(0, motivationalStringArray.size-1)]
    }

    override fun onStart() {
        super.onStart()
        DetailedPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        DetailedPresenter.detachScreen()
    }

    override fun restoreDailyObjectFromString() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        TODO("Not yet implemented")
    }
}