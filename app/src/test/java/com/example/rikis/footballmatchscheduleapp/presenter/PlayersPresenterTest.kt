package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.Players
import com.example.rikis.footballmatchscheduleapp.data.PlayersResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayersView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayersPresenterTest {

    @Mock
    private
    lateinit var viewPlayer: PlayersView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayersPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayersPresenter(viewPlayer, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getPlayersList() {
        val players: MutableList<Players> = mutableListOf()
        val response = PlayersResponse(players)
        val idTeam = "133604"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getPlayers(idTeam)),
                PlayersResponse::class.java
        )).thenReturn(response)

        presenter.getPlayersList(idTeam)
        Thread.sleep(5000)
        Mockito.verify(viewPlayer).showPlayersList(players)


    }
}