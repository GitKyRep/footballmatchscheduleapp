package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.ListTeam

interface TeamDetailView {
    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(data: List<ListTeam>)
}