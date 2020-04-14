package com.zsami.covid_19stats.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.presenters.DailyListPresenter
import com.zsami.covid_19stats.screens.DailyListScreen

class DailyListActivity : AppCompatActivity(), DailyListScreen {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.daily_list_activity)
    }

    override fun onStart() {
        super.onStart()
        DailyListPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        DailyListPresenter.detachScreen()
    }

    override fun dailyListFoo() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        TODO("Not yet implemented")
    }
}