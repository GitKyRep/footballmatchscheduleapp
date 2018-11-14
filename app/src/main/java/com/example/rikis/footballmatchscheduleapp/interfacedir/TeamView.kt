package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.Team

interface TeamView {
    fun showTeamHome(dataTeam: List<Team>)
    fun showTeamAway(dataTeam: List<Team>)
}