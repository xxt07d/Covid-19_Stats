package com.zsami.covid_19stats.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zsami.covid_19stats.R
import com.zsami.covid_19stats.data.Daily
import java.time.LocalDate


class DailyRecyclerViewAdapter(private val myDataset: List<Daily>) :
    RecyclerView.Adapter<DailyRecyclerViewAdapter.DailyRecyclerViewHolder>() {
    private lateinit var onItemClickListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemclick(position: Int)
    }

    fun setOnItemClickListener (listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    class DailyRecyclerViewHolder(itemView: View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        var mTextView1: TextView = itemView.findViewById(R.id.textView)
        var mTextView2: TextView = itemView.findViewById(R.id.textView2)

        init {
            itemView.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    listener.onItemclick(adapterPosition)
                }
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyRecyclerViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.daily_list_item, parent, false)

        return DailyRecyclerViewHolder(v, onItemClickListener)
    }

    override fun onBindViewHolder(holder: DailyRecyclerViewHolder, position: Int) {
        val currentDate: String = myDataset[position].date.substring(0,10)
        var newCases: Int = 0
        newCases = if (position < myDataset.size-1) {
            myDataset[position].infections.toInt() - myDataset[position+1].infections.toInt()
        } else {
            myDataset[position].infections.toInt()
        }
        val currentCasesText: String = "${newCases} new cases"
        holder.mTextView1.text = currentDate
        holder.mTextView2.text = currentCasesText
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}