package com.zsami.covid_19stats.activites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.presenters.AboutPresenter
import com.zsami.covid_19stats.screens.AboutScreen

class AboutActivity : AppCompatActivity(), AboutScreen {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
    }

    override fun onStart() {
        super.onStart()
        AboutPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        AboutPresenter.detachScreen()
    }

    override fun aboutFoo() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        TODO("Not yet implemented")
    }
}