package com.example.rikis.footballmatchscheduleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Team
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamView
import com.example.rikis.footballmatchscheduleapp.presenter.TeamPresenter
import com.example.rikis.footballmatchscheduleapp.utils.getLongDate
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_schedule.*


class DetailSchedule : AppCompatActivity(), TeamView {

    //private var teams1: MutableList<Team> = mutableListOf()
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamPresenter

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Match Detail")

        val lblIdEvent:String = intent.getStringExtra("idEvent")
        val lblStrHomeTeam:String = intent.getStringExtra("strHomeTeam")
        val lblStrAwayTeam:String = intent.getStringExtra("strAwayTeam")
        val lblIntHomeScore:String = intent.getStringExtra("intHomeScore")
        val lblIntAwayScore:String = intent.getStringExtra("intAwayScore")
        val lblDateEvent:String = intent.getStringExtra("dateEvent")
        val lblStrHomeGoalDetails:String = intent.getStringExtra("strHomeGoalDetails")
        val lblStrAwayGoalDetails:String = intent.getStringExtra("strAwayGoalDetails")
        val lblIntHomeShots:String = intent.getStringExtra("intHomeShots")
        val lblIntAwayShots:String = intent.getStringExtra("intAwayShots")
        val lblStrHomeLineupGoalkeeper:String = intent.getStringExtra("strHomeLineupGoalkeeper")
        val lblStrAwayLineupGoalkeeper:String = intent.getStringExtra("strAwayLineupGoalkeeper")
        val lblStrHomeLineupDefense:String = intent.getStringExtra("strHomeLineupDefense")
        val lblStrAwayLineupDefense:String = intent.getStringExtra("strAwayLineupDefense")
        val lblStrHomeLineupMidfield:String = intent.getStringExtra("strHomeLineupMidfield")
        val lblStrAwayLineupMidfield:String = intent.getStringExtra("strAwayLineupMidfield")
        val lblStrHomeLineupForward:String = intent.getStringExtra("strHomeLineupForward")
        val lblStrAwayLineupForward:String = intent.getStringExtra("strAwayLineupForward")
        val lblStrHomeLineupSubstitutes:String = intent.getStringExtra("strHomeLineupSubstitutes")
        val lblStrAwayLineupSubstitutes:String = intent.getStringExtra("strAwayLineupSubstitutes")
        val lblIdHomeTeam:String = intent.getStringExtra("idHomeTeam")
        val lblIdAwayTeam:String = intent.getStringExtra("idAwayTeam")


        //manggil presenter
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamPresenter(this, request, gson)
        presenter.getDetailTeam(lblIdHomeTeam,"home");
        presenter.getDetailTeam(lblIdAwayTeam,"away");

        val tanggalEvent = getLongDate(lblDateEvent)

        dateEvent.text = tanggalEvent
        strHomeTeam.text = if (lblStrHomeTeam.equals("null")) "" else lblStrHomeTeam
        strAwayTeam.text = if(lblStrAwayTeam.equals("null")) "" else lblStrAwayTeam
        intHomeScore.text = if(lblIntHomeScore.equals("null")) "" else lblIntHomeScore
        intAwayScore.text = if(lblIntAwayScore.equals("null")) "" else lblIntAwayScore
        strHomeGoalDetails.text = if(lblStrHomeGoalDetails.equals("null")) "" else lblStrHomeGoalDetails.replace(";","\n")
        strAwayGoalDetails.text = if(lblStrAwayGoalDetails.equals("null")) "" else lblStrAwayGoalDetails.replace(";","\n")
        intHomeShots.text = if(lblIntHomeShots.equals("null")) "" else lblIntHomeShots
        intAwayShots.text = if(lblIntAwayShots.equals("null")) "" else lblIntAwayShots
        strHomeLineupGoalkeeper.text = if(lblStrHomeLineupGoalkeeper.equals("null")) "" else lblStrHomeLineupGoalkeeper.replace("; ","\n")
        strAwayLineupGoalkeeper.text = if(lblStrAwayLineupGoalkeeper.equals("null")) "" else lblStrAwayLineupGoalkeeper.replace("; ","\n")
        strHomeLineupDefense.text = if(lblStrHomeLineupDefense.equals("null")) "" else lblStrHomeLineupDefense.replace("; ","\n")
        strAwayLineupDefense.text = if(lblStrAwayLineupDefense.equals("null")) "" else lblStrAwayLineupDefense.replace("; ","\n")
        strHomeLineupMidfield.text = if(lblStrHomeLineupMidfield.equals("null")) "" else lblStrHomeLineupMidfield.replace("; ","\n")
        strAwayLineupMidfield.text = if(lblStrAwayLineupMidfield.equals("null")) "" else lblStrAwayLineupMidfield.replace("; ","\n")
        strHomeLineupForward.text = if(lblStrHomeLineupForward.equals("null")) "" else lblStrHomeLineupForward.replace("; ","\n")
        strAwayLineupForward.text = if(lblStrAwayLineupForward.equals("null")) "" else lblStrAwayLineupForward.replace("; ","\n")
        strHomeLineupSubstitutes.text = if(lblStrHomeLineupSubstitutes.equals("null")) "" else lblStrHomeLineupSubstitutes.replace("; ","\n")
        strAwayLineupSubstitutes.text = if(lblStrAwayLineupSubstitutes.equals("null")) "" else lblStrAwayLineupSubstitutes.replace("; ","\n")

    }

    override fun showTeamHome(dataTeam: List<Team>) {
        teams.addAll(dataTeam)
        teams.map {
            Picasso.get().load(it.strTeamBadge.toString()).resize(100,100).into(strTeamBadgeHome)
        }
    }

    override fun showTeamAway(dataTeam: List<Team>) {
        teams.addAll(dataTeam)
        teams.map {
            Picasso.get().load(it.strTeamBadge.toString()).resize(100,100).into(strTeamBadgeAway)
        }
    }
}
