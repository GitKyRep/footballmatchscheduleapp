package com.example.rikis.footballmatchscheduleapp.presenter

import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.api.TheSportDbApi
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.data.EventResponse
import com.example.rikis.footballmatchscheduleapp.interfacedir.EventDetailView
import com.example.rikis.footballmatchscheduleapp.utils.TestContextProvider
import com.google.gson.Gson
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class EventDetailPresenterTest {

    @Mock
    private
    lateinit var viewTeam: EventDetailView

    @Mock
    private
    lateinit var gson: Gson

    @Mock
    private
    lateinit var apiRepository: ApiRepository

    private lateinit var presenter: EventDetailPresenter


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = EventDetailPresenter(viewTeam, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getEventDetail() {
        val events: MutableList<Event> = mutableListOf()
        val response = EventResponse(events)
        val eventId = "441613"

        `when`(gson.fromJson(apiRepository
                .doRequest(TheSportDbApi.getDetailEvent(eventId)),
                EventResponse::class.java
        )).thenReturn(response)

        presenter.getEventDetail(eventId)
        Thread.sleep(5000)
        Mockito.verify(viewTeam).showLoading()
        Mockito.verify(viewTeam).showEventDetail(events)
        Mockito.verify(viewTeam).hideLoading()
    }
}