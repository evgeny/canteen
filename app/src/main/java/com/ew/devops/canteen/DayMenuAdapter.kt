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
    private val workingDays:ArrayList<Date>

    init {
        val menuDate = Calendar.getInstance()
        workingDays = fillDays(menuDate)
    }

    /**
     * fill date list with working days up to end of next week
     */
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
        val menuDate = workingDays[position]

        val formattedDate = dateFormatter.format(menuDate)
        return DayMenuFragment.newInstance(formattedDate)
    }

    override fun getCount(): Int = workingDays.size

    override fun getPageTitle(position: Int): CharSequence =
            dateFormatterTab.format(workingDays[position])
}
