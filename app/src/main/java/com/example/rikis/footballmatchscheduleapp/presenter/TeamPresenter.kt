package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.TeamResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamPresenter(private val view: TeamView, private val apiRepository: ApiRepository, private val gson: Gson,
                    private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getDetailTeam(idTeam: String?, typeTeam: String?) {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDbApi.getDetailTeam(idTeam)),
                        TeamResponse::class.java
                )
            }
            if (typeTeam.equals("home")) {
                view.showTeamHome(data.await().teams)
            } else {
                view.showTeamAway(data.await().teams)
            }
        }
    }
}