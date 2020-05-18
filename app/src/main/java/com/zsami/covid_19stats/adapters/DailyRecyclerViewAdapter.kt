package com.zsami.covid_19stats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.network.Daily


class DailyRecyclerViewAdapter(private val myDataset: List<Daily>) :
    RecyclerView.Adapter<DailyRecyclerViewAdapter.DailyRecyclerViewHolder>() {

    class DailyRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mTextView1: TextView = itemView.findViewById(R.id.textView)
        var mTextView2: TextView = itemView.findViewById(R.id.textView2)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyRecyclerViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_list_item, parent, false)

        return DailyRecyclerViewHolder(v)
    }

    override fun onBindViewHolder(holder: DailyRecyclerViewHolder, position: Int) {
        val currentDate: String = myDataset[position].Date
        var newCases: Int = 0
        if (position > 0) {
            newCases = myDataset[position].Confirmed.toInt() - myDataset[position].Confirmed.toInt()
        } else {
            newCases = myDataset[position].Confirmed.toInt()
        }
        val currentCasesText: String = "${newCases} new cases"
        holder.mTextView1.text = currentDate
        holder.mTextView2.text = currentCasesText
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}