package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.ListTeam

interface ListTeamsView{
    fun showLoading()
    fun hideLoading()
    fun showTeamList(data:List<ListTeam>)
}