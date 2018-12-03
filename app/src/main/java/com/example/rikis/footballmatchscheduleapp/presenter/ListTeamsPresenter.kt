package com.example.rikis.footballmatchscheduleapp.presenter


import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.ListTeamsResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.ListTeamsView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class ListTeamsPresenter(private val view: ListTeamsView, private val apiRepository: ApiRepository, private val gson: Gson
                         , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamList(idLeague: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getTeamsByIdLeague(idLeague)),
                        ListTeamsResponse::class.java
                )
            }
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }

    fun getSearchTeam(namaTeam: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getSearchTeam(namaTeam)),
                        ListTeamsResponse::class.java
                )
            }

            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}