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
import kotlinx.android.synthetic.main.frag_schedule.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor


class FragSchedule : Fragment(), MainView, LeagueView {

    //inisialisasi widget yang dibutuhkan
    private var events: MutableList<Event> = mutableListOf()
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var presenterLeague: LeaguePresenter
    private lateinit var adapterEvent: RecyclerViewAdapter
    private lateinit var leagueId: String


    /*private fun addEventToCalender(event: Event) {
        val intent = Intent(Intent.ACTION_INSERT)
        intent.data = CalendarContract.Events.CONTENT_URI
        intent.putExtra(CalendarContract.Events.TITLE, event.event)
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                ("${event.dateEvent}.${event.time}").toDate().time)
        intent.putExtra(CalendarContract.Events.ALL_DAY, false)
        intent.putExtra(CalendarContract.Events.DESCRIPTION, event.filename)
        startActivity(intent)
    }*/

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
        listEvent.layoutManager = llm
        listEvent.adapter = adapterEvent

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
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
        cmbLeague.adapter = spinnerAdapter

        cmbLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val liga = parent.getItemAtPosition(position) as League
                //toast("Id nya: "+liga.idLeague)

                val leagueId: String = liga.idLeague
                val url = arguments!!.getString(getString(R.string.url))
                if (url.equals(getString(R.string.last))) {
                    presenter.getLastEventList(leagueId)
                } else {
                    presenter.getNextEventList(leagueId)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }
}
