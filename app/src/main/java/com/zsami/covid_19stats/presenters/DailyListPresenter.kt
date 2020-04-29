package com.zsami.covid_19stats.presenters

import com.zsami.covid_19stats.interactors.NetworkInteractor
import com.zsami.covid_19stats.screens.DailyListScreen

object DailyListPresenter : Presenter<DailyListScreen>() {
    var networkInteractor: NetworkInteractor? = null;

    override fun attachScreen(screen: DailyListScreen) {
        super.attachScreen(screen)
    }

    override fun detachScreen() {
        super.detachScreen()
    }

    fun doDailyListStuff() {
        //TODO
    }
}