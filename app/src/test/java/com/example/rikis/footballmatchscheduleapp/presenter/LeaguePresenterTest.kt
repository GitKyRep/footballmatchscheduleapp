package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.League
import com.example.rikis.footballmatchscheduleapp.data.LeagueResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.LeagueView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeaguePresenterTest {

    @Mock
    private
    lateinit var viewLeague: LeagueView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: LeaguePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeaguePresenter(viewLeague, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getAllLeague() {
        val league: MutableList<League> = mutableListOf()
        val response = LeagueResponse(league)

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getAllLeague()),
                LeagueResponse::class.java
        )).thenReturn(response)

        presenter.getAllLeague()
        Thread.sleep(5000)
        Mockito.verify(viewLeague).showAllLeague(league)

    }
}