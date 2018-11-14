package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.Event

interface EventDetailView {
    fun showLoading()
    fun hideLoading()
    fun showEventDetail(data: List<Event>)
}