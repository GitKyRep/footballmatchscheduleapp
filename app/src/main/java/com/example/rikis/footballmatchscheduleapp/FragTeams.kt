package com.example.rikis.footballmatchscheduleapp


import android.content.Context
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.*
import com.example.rikis.footballmatchscheduleapp.adapter.TeamsAdapter
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.League
import com.example.rikis.footballmatchscheduleapp.data.ListTeam
import com.example.rikis.footballmatchscheduleapp.interfacedir.LeagueView
import com.example.rikis.footballmatchscheduleapp.interfacedir.ListTeamsView
import com.example.rikis.footballmatchscheduleapp.presenter.LeaguePresenter
import com.example.rikis.footballmatchscheduleapp.presenter.ListTeamsPresenter
import com.example.rikis.footballmatchscheduleapp.utils.invisible
import com.example.rikis.footballmatchscheduleapp.utils.visible
import com.google.gson.Gson
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.*

class FragTeams : Fragment(), AnkoComponent<Context>, ListTeamsView,LeagueView {

    private var teams: MutableList<ListTeam> = mutableListOf()
    private lateinit var presenter: ListTeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinnerLeague: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private var leagues: MutableList<League> = mutableListOf()
    private lateinit var presenterLeague: LeaguePresenter
    private lateinit var leagueId: String

    private var menuItem: Menu? = null

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_btn -> {
                startActivity(intentFor<SearchTeam>().singleTop())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter = TeamsAdapter(teams) {
            ctx.startActivity<TeamDetailActivity>("id" to "${it.teamId}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = ListTeamsPresenter(this, request, gson)

        presenterLeague = LeaguePresenter(this, request, gson)
        presenterLeague.getAllLeague()

        swipeRefresh.onRefresh {
            presenter.getTeamList(leagueName)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui) {
        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            spinnerLeague = spinner {
                id = R.id.spinnerLeague
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(R.color.colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = R.id.list_team
                        lparams(width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar = progressBar {
                    }.lparams {
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<ListTeam>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }


    override fun showAllLeague(data: List<League>) {
        //val spinnerItems = resources.getStringArray(league)
        leagues.clear()
        leagues.addAll(data)

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, leagues)
        spinnerLeague.adapter = spinnerAdapter
        spinnerLeague.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val liga = parent.getItemAtPosition(position) as League
                val leagueId: String = liga.idLeague
                val idCup: String = liga.idCup
                //leagueName = spinnerLeague.selectedItem.toString()
                if(idCup.equals("0")){
                    presenter.getTeamList(leagueId)
                }else{
                    alert("Data Not Available").show()
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

    }
}
