package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.PlayerDetailResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayerDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val apiRepository: ApiRepository,private val gson: Gson,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetail(idPlayer: String) {

     /*   async(UI) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getPlayerDetail(idPlayer)),
                        PlayerDetailResponse::class.java)
            }

            Log.e("Respon data : ",data.await().toString())

            view.showPlayerDetail(data.await().players)
        }*/

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getPlayerDetail(idPlayer)),
                        PlayerDetailResponse::class.java
                )
            }
            //Log.e("Player Data",data.await().player.toString())
            view.showPlayerDetail(data.await().players)

        }
    }
}