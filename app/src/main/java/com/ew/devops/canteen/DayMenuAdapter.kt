package com.ew.devops.canteen

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.text.SimpleDateFormat
import java.util.*


class DayMenuAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {

    val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun getItem(position: Int): Fragment {
        val menuDate = Calendar.getInstance()
        menuDate.add(Calendar.DAY_OF_MONTH, position)

        return DayMenuFragment.newInstance(dateFormatter.format(menuDate.time))
    }

    override fun getCount(): Int {
        return 10 // limit two weeks = 10 work days
    }

    override fun getPageTitle(position: Int): CharSequence {
        val menuDate = Calendar.getInstance()
        menuDate.add(Calendar.DAY_OF_MONTH, position)
        return dateFormatter.format(menuDate.time)
    }
}
