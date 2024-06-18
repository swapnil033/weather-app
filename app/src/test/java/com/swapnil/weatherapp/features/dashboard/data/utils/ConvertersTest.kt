package com.swapnil.weatherapp.features.dashboard.data.utils

import android.util.Log
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import com.google.common.truth.Truth.assertThat

class ConvertersTest {

    var time = LocalDateTime.now()

    @Before
    fun setUp() {
        val timeString = "2024-06-17T00:00"
        time = LocalDateTime.parse(timeString, DateTimeFormatter.ISO_DATE_TIME)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun fromTimestamp() {
        val timeLong = Converters.dateToTimestamp(time)

        val timeDate = Converters.fromTimestamp(timeLong)

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        println("fromTimestamp: ${time.format(formatter)}")
        timeDate?.let {
            println("fromTimestamp: ${timeDate.format(formatter)}")
        }

        assertThat(timeDate).isEqualTo(time)
    }

    @Test
    fun dateToTimestamp() {
    }
}