package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.ListTeamsResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson) {

    fun getTeamDetail(teamId: String) {
        view.showLoading()

        async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getDetailTeam(teamId)),
                        ListTeamsResponse::class.java)
            }

            view.hideLoading()
            view.showTeamDetail(data.await().teams)
        }
    }
}