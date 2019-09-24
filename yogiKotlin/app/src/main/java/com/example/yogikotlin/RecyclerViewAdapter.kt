package com.example.yogikotlin

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class RecyclerViewAdapter(val mainActivity: MainActivity) : RecyclerView.Adapter<ViewHolderHelper>() {

    val baseCalendar = BaseCalendar()

    init {
        baseCalendar.initBaseCalendar {
            refreshView(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHelper {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_schedule, parent, false)
        return ViewHolderHelper(view)
    }

    override fun getItemCount(): Int {
        return BaseCalendar.calendarLow * BaseCalendar.weekLow
    }

    override fun onBindViewHolder(holder: ViewHolderHelper, position: Int) {

        if (position % BaseCalendar.weekLow == 0) holder.tv_date.setTextColor(
            Color.parseColor(
                "#ff1200"
            )
        )
        else holder.tv_date.setTextColor(Color.parseColor("#676d6e"))

        if (position < baseCalendar.prevMonthTailOffset || position >= baseCalendar.prevMonthTailOffset + baseCalendar.currentMonthMaxDate) {
            holder.tv_date.alpha = 0.3f
        } else {
            holder.tv_date.alpha = 1f
        }
        holder.tv_date.text = baseCalendar.data[position].toString()
    }

    fun changeToPrevMonth() {
        baseCalendar.changeToPrevMonth {
            refreshView(it)
        }
    }

    fun changeToNextMonth() {
        baseCalendar.changeToNextMonth {
            refreshView(it)
        }
    }

    private fun refreshView(calendar: Calendar) {
        notifyDataSetChanged()
        mainActivity.refreshCurrentMonth(calendar)
    }
}