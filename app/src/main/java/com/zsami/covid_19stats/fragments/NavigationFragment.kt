package com.zsami.covid_19stats.fragments

import androidx.fragment.app.Fragment
import com.zsami.covid_19stats.presenters.NavigationPresenter
import com.zsami.covid_19stats.screens.NavigationScreen

class NavigationFragment : Fragment(), NavigationScreen {

    override fun onStart() {
        super.onStart()
        NavigationPresenter.attachScreen(this)
    }

    override fun onStop() {
        super.onStop()
        NavigationPresenter.detachScreen()
    }

    override fun navigationFoo() {
        TODO("Not yet implemented")
    }

    override fun foo() {
        TODO("Not yet implemented")
    }
}