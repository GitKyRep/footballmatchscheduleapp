package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.PlayerDetailResponse
import com.example.rikis.footballmatchscheduleapp.data.Players
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayerDetailView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {
    @Mock
    private
    lateinit var viewPlayerDetail: PlayerDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerDetailPresenter(viewPlayerDetail, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getPlayerDetail() {

        val players: MutableList<Players> = mutableListOf()
        val response = PlayerDetailResponse(players)
        val idPlayer = "34145937"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getPlayerDetail(idPlayer)),
                PlayerDetailResponse::class.java
        )).thenReturn(response)

        presenter.getPlayerDetail(idPlayer)
        Thread.sleep(5000)
        Mockito.verify(viewPlayerDetail).showPlayerDetail(players)

    }
}