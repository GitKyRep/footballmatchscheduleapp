package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.DetailSchedule
import com.example.rikis.footballmatchscheduleapp.data.TeamResponse
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamPresenter(private val view: DetailSchedule, private val apiRepository: ApiRepository, private val gson: Gson){

    fun getDetailTeam(idTeam:String?,typeTeam:String?){
        doAsync {

            val data =  gson.fromJson(apiRepository.
                    doRequest(TheSportDbApi.getDetailTeam(idTeam)),
                    TeamResponse::class.java)

            uiThread {
                if(typeTeam.equals("home")){
                    view.showTeamHome(data.teams)
                }else{
                    view.showTeamAway(data.teams)
                }

            }

        }
    }
}