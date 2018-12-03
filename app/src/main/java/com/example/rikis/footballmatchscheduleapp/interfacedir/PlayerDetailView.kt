package com.example.rikis.footballmatchscheduleapp.interfacedir
import com.example.rikis.footballmatchscheduleapp.data.Players

interface PlayerDetailView {
    fun showPlayerDetail(data: List<Players>)
}