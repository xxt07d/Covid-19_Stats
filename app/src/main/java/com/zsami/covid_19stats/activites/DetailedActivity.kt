package com.zsami.covid_19stats.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.presenters.DetailedPresenter
import com.zsami.covid_19stats.screens.DetailedScreen

class DetailedActivity : AppCompatActivity(), DetailedScreen {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailed_activity)
    }

    override fun onStart() {
        super.onStart()
        DetailedPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        DetailedPresenter.detachScreen()
    }

    override fun detailedFoo() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        TODO("Not yet implemented")
    }
}