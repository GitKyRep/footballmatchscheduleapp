package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.EventResponse
import com.example.rikis.footballmatchscheduleapp.FragSchedule
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.google.gson.Gson
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainPresenter(private val view: FragSchedule, private val apiRepository: ApiRepository, private val gson: Gson){

    fun getNextEventList(leagueId:String?){
        view.showLoading()

        doAsync {

            val data =  gson.fromJson(apiRepository.
                    doRequest(TheSportDbApi.getNextEvent(leagueId)),
                    EventResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEvent(data.events)
            }

        }
    }

    fun getLastEventList(leagueId:String?){
        view.showLoading()

        doAsync {

            val data =  gson.fromJson(apiRepository.
                    doRequest(TheSportDbApi.getLastEvent(leagueId)),
                    EventResponse::class.java)

            uiThread {
                view.hideLoading()
                view.showEvent(data.events)
            }

        }
    }
}