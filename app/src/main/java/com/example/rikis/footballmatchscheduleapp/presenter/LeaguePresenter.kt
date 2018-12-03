package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.LeagueResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.LeagueView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class LeaguePresenter(private val view: LeagueView, private val apiRepository: ApiRepository, private val gson: Gson
                    , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getAllLeague() {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDbApi.getAllLeague()),
                        LeagueResponse::class.java
                )
            }
            view.showAllLeague(data.await().countrys)
        }
    }
}