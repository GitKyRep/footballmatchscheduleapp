package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.data.SearchEventResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private
    lateinit var viewSearchMatch: MainView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: SearchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchPresenter(viewSearchMatch, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearchEventList() {
        val Event: MutableList<Event> = mutableListOf()
        val response = SearchEventResponse(Event)
        val namaEvent = "barcelona"

        Mockito.`when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getSearchEvent(namaEvent)),
                SearchEventResponse::class.java
        )).thenReturn(response)

        presenter.getSearchEventList(namaEvent)
        Thread.sleep(5000)
        Mockito.verify(viewSearchMatch).showEvent(Event)

    }
}