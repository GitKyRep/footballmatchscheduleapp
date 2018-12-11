package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.data.EventResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test

import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class GetNextEventListTest {
    @Mock
    private
    lateinit var viewTeam: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(viewTeam, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getNextEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getNextEvent(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getNextEventList(leagueId)
        Thread.sleep(5000)

        Mockito.verify(viewTeam).showLoading()
        Mockito.verify(viewTeam).showEvent(events)
        Mockito.verify(viewTeam).hideLoading()
    }

}