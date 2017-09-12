package com.ew.devops.canteen

import com.nhaarman.mockito_kotlin.mock
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.*

class DayMenuAdapterTest {
    private lateinit var dayMenuAdapter: DayMenuAdapter

    @Before
    fun setUp() {
        dayMenuAdapter = DayMenuAdapter(mock())
    }

    @Test
    fun weekListTest() {
        val date = Calendar.getInstance()
        date.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
        date.timeInMillis
        var days = dayMenuAdapter.fillDays(date)
        assertEquals("days count", 6, days.size)

        date.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        date.timeInMillis
        days = dayMenuAdapter.fillDays(date)
        assertEquals("days count if today is sunday", 10, days.size)

        date.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
        date.timeInMillis
        days = dayMenuAdapter.fillDays(date)
        assertEquals("days count if today is saturday", 10, days.size)
    }
}