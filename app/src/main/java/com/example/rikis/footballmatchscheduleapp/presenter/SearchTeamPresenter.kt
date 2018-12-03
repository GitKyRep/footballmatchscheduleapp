package com.example.rikis.footballmatchscheduleapp.presenter

import android.util.Log
import com.example.rikis.footballmatchscheduleapp.SearchTeam
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.ListTeamsResponse
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class SearchTeamPresenter(private val view: SearchTeam, private val apiRepository: ApiRepository, private val gson: Gson
                         , private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchTeam(namaTeam: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getSearchTeam(namaTeam)),
                        ListTeamsResponse::class.java
                )
            }
            Log.e("TAG","response search : "+data.await().teams)
            view.showTeamList(data.await().teams)
            view.hideLoading()
        }
    }
}