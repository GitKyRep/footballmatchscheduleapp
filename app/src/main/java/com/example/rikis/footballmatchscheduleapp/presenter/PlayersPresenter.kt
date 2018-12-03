package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.PlayersResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayersView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayersPresenter(private val view: PlayersView, private val apiRepository: ApiRepository, private val gson: Gson
                         , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayersList(idTeam: String?) {

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getPlayers(idTeam)),
                        PlayersResponse::class.java
                )
            }
            //Log.e("Player Data",data.await().player.toString())
            view.showPlayersList(data.await().player)

        }
    }
}