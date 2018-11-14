package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.Event

interface MainView {
    fun showLoading()
    fun hideLoading()
    fun showEvent(data: List<Event>)
}