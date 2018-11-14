package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.Team
import com.example.rikis.footballmatchscheduleapp.data.TeamResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GetDetailTeamHomeTest {

    @Mock
    private
    lateinit var viewTeam: TeamView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPresenter(viewTeam, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getDetailTeamHome() {
        val teams: MutableList<Team> = mutableListOf()
        val response = TeamResponse(teams)
        val teamId = "133604"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getDetailTeam(teamId)),
                TeamResponse::class.java
        )).thenReturn(response)

        presenter.getDetailTeam(teamId, "home")
        Thread.sleep(5000)

        Mockito.verify(viewTeam).showTeamHome(teams)

    }

}