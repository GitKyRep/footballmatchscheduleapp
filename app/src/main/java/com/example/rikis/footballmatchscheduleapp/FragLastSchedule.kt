package com.example.rikis.footballmatchscheduleapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.rikis.footballmatchscheduleapp.adapter.RecyclerViewAdapter
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.data.League
import com.example.rikis.footballmatchscheduleapp.interfacedir.LeagueView
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.example.rikis.footballmatchscheduleapp.presenter.LeaguePresenter
import com.example.rikis.footballmatchscheduleapp.presenter.MainPresenter
import com.example.rikis.footballmatchscheduleapp.utils.invisible
import com.example.rikis.footballmatchscheduleapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_schedule_last.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor


class FragLastSchedule : Fragment(), MainView, LeagueView {

    //inisialisasi widget yang dibutuhkan
    private var events: MutableList<Event> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var presenterLeague: LeaguePresenter
    private lateinit var adapterEvent: RecyclerViewAdapter
    private lateinit var leagueId: String

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        presenterLeague = LeaguePresenter(this, request, gson)
        presenterLeague.getAllLeague()

        presenter = MainPresenter(this, request, gson)

        adapterEvent = RecyclerViewAdapter(ctx, events) {
            startActivity(intentFor<DetailSchedule>("idEvent" to "${it.idEvent}").singleTop())
            //calendarEvent()
        }

        val llm = LinearLayoutManager(ctx)
        llm.orientation = LinearLayoutManager.VERTICAL
        listEventLast.layoutManager = llm
        listEventLast.adapter = adapterEvent

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_schedule_last, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun showLoading() {
        progressBarLast.visible()
    }

    override fun hideLoading() {
        progressBarLast.invisible()
    }

    override fun showEvent(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapterEvent.notifyDataSetChanged()
    }

    override fun showAllLeague(data: List<League>) {
        leagues.clear()
        leagues.addAll(data)

        //val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        //spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cmbLeagueLast.adapter = spinnerAdapter

        cmbLeagueLast.post({ cmbLeagueLast.setSelection(40) })

        cmbLeagueLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val liga = parent.getItemAtPosition(position) as League
                leagueId= liga.idLeague
                presenter.getLastEventList(leagueId)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

}
