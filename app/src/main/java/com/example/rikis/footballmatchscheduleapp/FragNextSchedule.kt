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
import kotlinx.android.synthetic.main.fragment_frag_next_schedule.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor

class FragNextSchedule : Fragment(), MainView, LeagueView {
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

            /*
            val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC")) as Calendar
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            sdf.timeZone = TimeZone.getTimeZone("UTC")

            cal.time = sdf.parse(it.dateEvent)
            var date: Long = cal.timeInMillis
            var strEvent = it.strEvent

            strNotif.setOnClickListener {

                val intent =    Intent(Intent.ACTION_INSERT)
                        .setType("vnd.android.cursor.item/event")
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.Events.TITLE, "Pertandingan ${strEvent}")
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date)
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, date + (60*60*1000))

                startActivity(intent)
            }*/

        }

        val llm = LinearLayoutManager(ctx)
        llm.orientation = LinearLayoutManager.VERTICAL
        listEventNext.layoutManager = llm
        listEventNext.adapter = adapterEvent

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.fragment_frag_next_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


    }

    override fun showLoading() {
        progressBarNext.visible()
    }

    override fun hideLoading() {
        progressBarNext.invisible()
    }

    override fun showEvent(data: List<Event>) {
        events.clear()
        events.addAll(data)
        adapterEvent.notifyDataSetChanged()
    }

    override fun showAllLeague(data: List<League>) {
        leagues.clear()
        leagues.addAll(data)
        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        cmbLeagueNext.adapter = spinnerAdapter
        //cmbLeagueNext.post(Runnable { cmbLeagueNext.setSelection(40) })
        cmbLeagueNext.post({ cmbLeagueNext.setSelection(40) })
        cmbLeagueNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val liga = parent.getItemAtPosition(position) as League
                val leagueId: String = liga.idLeague
                presenter.getNextEventList(leagueId)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }

}
