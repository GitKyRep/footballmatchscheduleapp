package com.example.rikis.footballmatchscheduleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rikis.footballmatchscheduleapp.adapter.RecyclerViewAdapter
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.interfacedir.MainView
import com.example.rikis.footballmatchscheduleapp.presenter.SearchPresenter
import com.example.rikis.footballmatchscheduleapp.utils.invisible
import com.example.rikis.footballmatchscheduleapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_matches.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.singleTop


class SearchMatches : AppCompatActivity(), MainView {

    private var events: MutableList<Event> = mutableListOf()
    private lateinit var presenterSearch: SearchPresenter
    private lateinit var adapterEvent: RecyclerViewAdapter
    private lateinit var namaEvent: String
    private lateinit var searchView: SearchView

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu.findItem(R.id.search)
        searchItem?.expandActionView()

        searchView = searchItem?.actionView as SearchView

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {

            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true // KEEP IT TO TRUE OR IT DOESN'T OPEN !!
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                // Do whatever you need
                finish()
                return true // OR FALSE IF YOU DIDN'T WANT IT TO CLOSE!
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (!query.equals(null)) {
                    if (query.length > 1) {
                        namaEvent = query
                        presenterSearch.getSearchEventList(namaEvent)

                    }
                }
                //searc"hView.clearFocus()

                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (!query.equals(null)) {
                    if (query.length > 1) {
                        //Log.e("TAG","response search : "+query.toString())
                        namaEvent = query
                        presenterSearch.getSearchEventList(namaEvent)
                    }
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_matches)

        val request = ApiRepository()
        val gson = Gson()
        presenterSearch = SearchPresenter(this,request,gson)
        adapterEvent = RecyclerViewAdapter(ctx, events) {
            startActivity(intentFor<DetailSchedule>("idEvent" to "${it.idEvent}").singleTop())
        }

        val llms = LinearLayoutManager(ctx)
        llms.orientation = LinearLayoutManager.VERTICAL
        listSearchEvent.layoutManager = llms
        listSearchEvent.adapter = adapterEvent

    }

    override fun showLoading() {
        progressBarSearch.visible()
    }

    override fun hideLoading() {
        progressBarSearch.invisible()
    }

    override fun showEvent(data: List<Event>) {
        events.clear()
        val list = data
        if(!list.equals(null)) {
            for (item in list) {
                if (item.strSport.equals("Soccer")){
                    Log.e("TAG","Response "+item)
                    events.add(item)
                }
            }
        }

        adapterEvent.notifyDataSetChanged()
    }

}
