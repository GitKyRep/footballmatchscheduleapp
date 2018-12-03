package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.League

interface LeagueView {
    fun showAllLeague(data: List<League>)
}