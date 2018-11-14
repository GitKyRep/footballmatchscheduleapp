package com.example.rikis.footballmatchscheduleapp

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.rikis.footballmatchscheduleapp.R.drawable.ic_add_to_favorites
import com.example.rikis.footballmatchscheduleapp.R.drawable.ic_added_to_favorites
import com.example.rikis.footballmatchscheduleapp.R.id.add_to_favorite
import com.example.rikis.footballmatchscheduleapp.R.menu.detail_menu
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.data.FavoritesEvent
import com.example.rikis.footballmatchscheduleapp.data.Team
import com.example.rikis.footballmatchscheduleapp.interfacedir.EventDetailView
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamView
import com.example.rikis.footballmatchscheduleapp.presenter.EventDetailPresenter
import com.example.rikis.footballmatchscheduleapp.presenter.TeamPresenter
import com.example.rikis.footballmatchscheduleapp.utils.database
import com.example.rikis.footballmatchscheduleapp.utils.getLongDate
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_schedule.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar


class DetailSchedule : AppCompatActivity(), TeamView, EventDetailView {

    //private var teams1: MutableList<Team> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var events: Event
    private lateinit var presenter: TeamPresenter
    private lateinit var presenterDetail: EventDetailPresenter

    //manggil presenter
    private val request = ApiRepository()
    private val gson = Gson()

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var id: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(FavoritesEvent.TABLE_FAVORITE, "(EVENT_ID = {id})",
                        "id" to id)
            }
            snackbar(swipe, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipe, e.localizedMessage).show()
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(FavoritesEvent.TABLE_FAVORITE,
                        FavoritesEvent.EVENT_ID to events.idEvent,
                        FavoritesEvent.HOME_TEAM to events.strHomeTeam,
                        FavoritesEvent.AWAY_TEAM to events.strAwayTeam,
                        FavoritesEvent.DATE_EVENT to events.dateEvent,
                        FavoritesEvent.HOME_SCORE to events.intHomeScore,
                        FavoritesEvent.AWAY_SCORE to events.intAwayScore)
            }
            snackbar(swipe, "Added to favorite").show()
        } catch (e: SQLiteConstraintException) {
            snackbar(swipe, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(FavoritesEvent.TABLE_FAVORITE)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoritesEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        id = intent.getStringExtra("idEvent")

        favoriteState()
        presenterDetail = EventDetailPresenter(this, request, gson)
        presenterDetail.getEventDetail(id)

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showEventDetail(data: List<Event>) {
        //Log.e("Response",data.toString())
        events = Event(data[0].idEvent,
                data[0].strHomeTeam,
                data[0].strAwayTeam,
                data[0].dateEvent,
                data[0].intHomeScore,
                data[0].intAwayScore,
                data[0].strHomeGoalDetails,
                data[0].strAwayGoalDetails,
                data[0].intHomeShots,
                data[0].intAwayShots,
                data[0].strHomeLineupGoalkeeper,
                data[0].strAwayLineupGoalkeeper,
                data[0].strHomeLineupDefense,
                data[0].strAwayLineupDefense,
                data[0].strHomeLineupMidfield,
                data[0].strAwayLineupMidfield,
                data[0].strHomeLineupForward,
                data[0].strAwayLineupForward,
                data[0].strHomeLineupSubstitutes,
                data[0].strAwayLineupSubstitutes,
                data[0].idHomeTeam,
                data[0].idAwayTeam)

        val tanggalEvent = getLongDate(data[0].dateEvent)
        dateEvent.text = tanggalEvent
        strHomeTeam.text = if (data[0].strHomeTeam.equals("null")) "" else data[0].strHomeTeam
        strAwayTeam.text = if (data[0].strAwayTeam.equals("null")) "" else data[0].strAwayTeam
        intHomeScore.text = if (data[0].intHomeScore.equals("null")) "" else data[0].intHomeScore
        intAwayScore.text = if (data[0].intAwayScore.equals("null")) "" else data[0].intAwayScore
        strHomeGoalDetails.text = if (data[0].strHomeGoalDetails.equals("null")) "" else data[0].strHomeGoalDetails?.replace(";", "\n")
        strAwayGoalDetails.text = if (data[0].strAwayGoalDetails.equals("null")) "" else data[0].strAwayGoalDetails?.replace(";", "\n")
        intHomeShots.text = if (data[0].intHomeShots.equals("null")) "" else data[0].intHomeShots
        intAwayShots.text = if (data[0].intAwayShots.equals("null")) "" else data[0].intAwayShots
        strHomeLineupGoalkeeper.text = if (data[0].strHomeLineupGoalkeeper.equals("null")) "" else data[0].strHomeLineupGoalkeeper?.replace("; ", "\n")
        strAwayLineupGoalkeeper.text = if (data[0].strAwayLineupGoalkeeper.equals("null")) "" else data[0].strAwayLineupGoalkeeper?.replace("; ", "\n")
        strHomeLineupDefense.text = if (data[0].strHomeLineupDefense.equals("null")) "" else data[0].strHomeLineupDefense?.replace("; ", "\n")
        strAwayLineupDefense.text = if (data[0].strAwayLineupDefense.equals("null")) "" else data[0].strAwayLineupDefense?.replace("; ", "\n")
        strHomeLineupMidfield.text = if (data[0].strHomeLineupMidfield.equals("null")) "" else data[0].strHomeLineupMidfield?.replace("; ", "\n")
        strAwayLineupMidfield.text = if (data[0].strAwayLineupMidfield.equals("null")) "" else data[0].strAwayLineupMidfield?.replace("; ", "\n")
        strHomeLineupForward.text = if (data[0].strHomeLineupForward.equals("null")) "" else data[0].strHomeLineupForward?.replace("; ", "\n")
        strAwayLineupForward.text = if (data[0].strAwayLineupForward.equals("null")) "" else data[0].strAwayLineupForward?.replace("; ", "\n")
        strHomeLineupSubstitutes.text = if (data[0].strHomeLineupSubstitutes.equals("null")) "" else data[0].strHomeLineupSubstitutes?.replace("; ", "\n")
        strAwayLineupSubstitutes.text = if (data[0].strAwayLineupSubstitutes.equals("null")) "" else data[0].strAwayLineupSubstitutes?.replace("; ", "\n")


        presenter = TeamPresenter(this, request, gson)
        presenter.getDetailTeam(data[0].idHomeTeam, "home")
        presenter.getDetailTeam(data[0].idAwayTeam, "away")

    }

    override fun showTeamHome(dataTeam: List<Team>) {
        teams.addAll(dataTeam)
        teams.map {
            Picasso.get().load(it.strTeamBadge.toString()).resize(100, 100).into(strTeamBadgeHome)
        }
    }

    override fun showTeamAway(dataTeam: List<Team>) {
        teams.addAll(dataTeam)
        teams.map {
            Picasso.get().load(it.strTeamBadge.toString()).resize(100, 100).into(strTeamBadgeAway)
        }
    }
}
