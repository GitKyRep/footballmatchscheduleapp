package com.example.rikis.footballmatchscheduleapp

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.rikis.footballmatchscheduleapp.R.drawable.ic_add_to_favorites
import com.example.rikis.footballmatchscheduleapp.R.drawable.ic_added_to_favorites
import com.example.rikis.footballmatchscheduleapp.R.id.add_to_favorite
import com.example.rikis.footballmatchscheduleapp.R.menu.detail_menu
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.FavoritesTeam
import com.example.rikis.footballmatchscheduleapp.data.ListTeam
import com.example.rikis.footballmatchscheduleapp.interfacedir.TeamDetailView
import com.example.rikis.footballmatchscheduleapp.presenter.TeamDetailPresenter
import com.example.rikis.footballmatchscheduleapp.utils.MyViewPagerAdapter
import com.example.rikis.footballmatchscheduleapp.utils.database
import com.example.rikis.footballmatchscheduleapp.utils.invisible
import com.example.rikis.footballmatchscheduleapp.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: ListTeam
    private lateinit var id: String

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    //fungsi untuk memasukan data detail team kedalam database sqllite favorite
    fun addToFavorite(){
        try {
            database.use {
                insert(FavoritesTeam.TABLE_FAVORITE_TEAM,
                        FavoritesTeam.TEAM_ID to teams.teamId,
                        FavoritesTeam.TEAM_NAME to teams.teamName,
                        FavoritesTeam.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(FavoritesTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                        "id" to id)
            }
            snackbar(swipeRefresh, "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)

        val intent = intent
        id = intent.getStringExtra("id")

        //setContentView(R.layout.activity_team_detail)
        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)
        /*swipeRefresh.onRefresh {
            presenter.getTeamDetail(id)
        }*/
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<ListTeam>) {

        teams = ListTeam(data[0].teamId,
                data[0].teamName,
                data[0].teamBadge)
        Log.e("TAG","Response : "+data[0])

        Picasso.get().load(data[0].teamBadge).into(teamBadgeTxt)
        teamNameTxt.text = data[0].teamName
        teamFormedYearTxt.text = data[0].teamFormedYear
        teamStadiumTxt.text = data[0].teamStadium

        val adapterDetailTeam = MyViewPagerAdapter(supportFragmentManager)

        val FragOverview = FragOverview()
        val mArgsLast = Bundle()
        mArgsLast.putString("strDescription", data[0].teamDescription)
        FragOverview.setArguments(mArgsLast)

        val FragPlayers= FragPlayers()
        val mArgsNext = Bundle()
        mArgsNext.putString("idTeam",data[0].teamId)
        FragPlayers.setArguments(mArgsNext)


        adapterDetailTeam.addFragment(FragOverview,"Overview")
        adapterDetailTeam.addFragment(FragPlayers,"Players")

        viewPagerTeam.adapter = adapterDetailTeam
        tabsTeam.setupWithViewPager(viewPagerTeam)


    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoritesTeam.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoritesTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

}
