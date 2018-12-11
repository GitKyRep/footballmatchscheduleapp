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
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetLastEventListTest {
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
    fun getLastEventList() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val leagueId = "4328"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getLastEvent(leagueId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getLastEventList(leagueId)
        Thread.sleep(5000)

        Mockito.verify(viewTeam).showLoading()
        Mockito.verify(viewTeam).showEvent(events)
        Mockito.verify(viewTeam).hideLoading()
    }
}