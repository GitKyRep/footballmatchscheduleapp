package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.ListTeam
import com.example.rikis.footballmatchscheduleapp.data.ListTeamsResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.ListTeamsView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class ListTeamsPresenterTest {
    @Mock
    private
    lateinit var viewTeam: ListTeamsView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: ListTeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ListTeamsPresenter(viewTeam, apiRepository, gson, TestContextProvider())
    }


    @Test
    fun getTeamList() {
        val listTeam: MutableList<ListTeam> = mutableListOf()
        val response = ListTeamsResponse(listTeam)
        val leagueId = "4328"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getTeamsByIdLeague(leagueId)),
                ListTeamsResponse::class.java
        )).thenReturn(response)

        presenter.getTeamList(leagueId)
        Thread.sleep(5000)

        Mockito.verify(viewTeam).showLoading()
        Mockito.verify(viewTeam).showTeamList(listTeam)
        Mockito.verify(viewTeam).hideLoading()
    }


    @Test
    fun getSearchTeam() {
        val listTeam: MutableList<ListTeam> = mutableListOf()
        val response = ListTeamsResponse(listTeam)
        val namaTeam = "Barcelona"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getTeamsByIdLeague(namaTeam)),
                ListTeamsResponse::class.java
        )).thenReturn(response)

        presenter.getSearchTeam(namaTeam)
        Thread.sleep(5000)

        Mockito.verify(viewTeam).showLoading()
        Mockito.verify(viewTeam).showTeamList(listTeam)
        Mockito.verify(viewTeam).hideLoading()
    }
}