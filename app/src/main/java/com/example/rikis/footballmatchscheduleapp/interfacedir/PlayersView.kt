package com.example.rikis.footballmatchscheduleapp.interfacedir

import com.example.rikis.footballmatchscheduleapp.data.Players

interface PlayersView{
    fun showPlayersList(data:List<Players>)
}