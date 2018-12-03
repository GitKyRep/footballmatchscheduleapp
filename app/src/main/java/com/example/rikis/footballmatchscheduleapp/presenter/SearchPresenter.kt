package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.SearchEventResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchPresenter(private val view: MainView, private val apiRepository: ApiRepository, private val gson: Gson
                    , private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getSearchEventList(namaEvent: String?) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDbApi.getSearchEvent(namaEvent)),
                        SearchEventResponse::class.java
                )
            }
            //Log.e("TAG","Response : "+data.await().event)
            view.showEvent(data.await().event)
            view.hideLoading()
        }
    }
}