package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.CoroutineContextProvider
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.EventResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.EventDetailView
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class EventDetailPresenter(private val view: EventDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getEventDetail(eventId: String) {
        view.showLoading()

        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository
                        .doRequest(TheSportDbApi.getDetailEvent(eventId)),
                        EventResponse::class.java
                )
            }
            view.hideLoading()
            view.showEventDetail(data.await().events)
        }
    }
}