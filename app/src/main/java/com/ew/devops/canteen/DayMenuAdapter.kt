package com.ew.devops.canteen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DayMenuAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val dateFormatterTab = SimpleDateFormat("EEE, d MMM", Locale.getDefault())
    private var dayOver = 0
    private var workingDays:ArrayList<Date>

    //TODO show rest from the current week, starting today + 5 week day of the next week
    init {
        val menuDate = Calendar.getInstance()
        val weekDay = menuDate.get(Calendar.DAY_OF_WEEK)
        workingDays = fillDays(menuDate)
    }

    fun fillDays(menuDate: Calendar): ArrayList<Date> {
        var weekCounter = 0
        val days = ArrayList<Date>()

        var dayOfWeek: Int
        do {
            // emit week of day and choose a next day relaying on it
            dayOfWeek = menuDate.get(Calendar.DAY_OF_WEEK)

            if (dayOfWeek == Calendar.SATURDAY) {
                menuDate.add(Calendar.DAY_OF_MONTH, 2)
                continue
            } else if (dayOfWeek == Calendar.SUNDAY) {
                menuDate.add(Calendar.DAY_OF_MONTH, 1)
                continue
            } else if (dayOfWeek == Calendar.FRIDAY) {
                weekCounter++
            }

            // add day to all-days-list
            days.add(Date(menuDate.timeInMillis))

            menuDate.add(Calendar.DAY_OF_MONTH, 1)
        } while (weekCounter < 2) //only for next two week

        return days
    }

    override fun getItem(position: Int): Fragment {
        val menuDate = Calendar.getInstance()
        menuDate.add(Calendar.DAY_OF_MONTH, position)

        val formattedDate = dateFormatter.format(menuDate.time)
        return DayMenuFragment.newInstance(formattedDate)
    }

    override fun getCount(): Int {
        var count = 5
        val today = Calendar.getInstance()
        val weekday = today.get(Calendar.DAY_OF_WEEK)
        count += when (weekday) {
            Calendar.MONDAY -> 5
            Calendar.TUESDAY -> 4
            Calendar.WEDNESDAY -> 3
            Calendar.THURSDAY -> 2
            Calendar.FRIDAY -> 1
            else -> 5
        }

        return count
    }

    override fun getPageTitle(position: Int): CharSequence {
        val menuDate = Calendar.getInstance()

        menuDate.add(Calendar.DAY_OF_MONTH, position)
        /*menuDate.add(Calendar.DAY_OF_MONTH, position + dayOver)

        if (menuDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayOver += 2
            menuDate.add(Calendar.DAY_OF_MONTH, 2)
        }*/

        return dateFormatterTab.format(menuDate.time)
    }

    private fun getMenuDate(position: Int): Calendar {
        val menuDate = Calendar.getInstance()
        menuDate.add(Calendar.DAY_OF_MONTH, position + dayOver)

        if (menuDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            dayOver += 2
            menuDate.add(Calendar.DAY_OF_MONTH, 2)
        }

        return menuDate
    }
}
