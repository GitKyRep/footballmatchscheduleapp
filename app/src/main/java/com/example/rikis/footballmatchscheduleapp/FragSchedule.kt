package com.example.rikis.footballmatchscheduleapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.adapter.RecyclerViewAdapter
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.example.rikis.footballmatchscheduleapp.presenter.MainPresenter
import com.example.rikis.footballmatchscheduleapp.utils.invisible
import com.example.rikis.footballmatchscheduleapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_schedule.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor


class FragSchedule : Fragment(), MainView {

    //inisialisasi widget yang dibutuhkan
    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenter: MainPresenter
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var leagueId: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {


        return inflater.inflate(R.layout.frag_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Inflate the layout for this fragment
        //set adapter
        //adapter =MainAdapter(events)
        adapter = RecyclerViewAdapter(ctx, events) {
            startActivity(intentFor<DetailSchedule>("idEvent" to "${it.idEvent}").singleTop())
        }

        val llm = LinearLayoutManager(ctx)
        llm.orientation = LinearLayoutManager.VERTICAL
        listEvent.layoutManager = llm
        listEvent.adapter = adapter


        //listEvent.adapter = adapter

        //manggil presenter
        val request = ApiRepository()
        val gson = Gson()
        presenter = MainPresenter(this, request, gson)

        //mengisi ke adapter
        //presenter.getNextEventList("4328");
        val leagueId: String = getString(R.string.leagueId)

        val url = arguments!!.getString(getString(R.string.url))
        if (url.equals(getString(R.string.last))) {
            presenter.getLastEventList(leagueId)
        } else {
            presenter.getNextEventList(leagueId)
        }

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
        adapter.notifyDataSetChanged()
    }
}
