package com.zsami.covid_19stats.recyclerviewitems

class DailyListItem {
    private var mText1: String? = null
    private var mText2: String? = null
    fun ExampleItem(imageResource: Int, text1: String?, text2: String?) {
        mText1 = text1
        mText2 = text2
    }

    fun getText1(): String? {
        return mText1
    }

    fun getText2(): String? {
        return mText2
    }
}