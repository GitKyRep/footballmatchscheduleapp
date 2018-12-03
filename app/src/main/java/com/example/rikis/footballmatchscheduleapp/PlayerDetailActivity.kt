package com.example.rikis.footballmatchscheduleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Players
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayerDetailView
import com.example.rikis.footballmatchscheduleapp.presenter.PlayerDetailPresenter
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_player_detail.*

class PlayerDetailActivity : AppCompatActivity(),PlayerDetailView {

    private lateinit var presenter: PlayerDetailPresenter
    private lateinit var player: Players
    private lateinit var idPlayer: String
    private lateinit var fanArt: String

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        idPlayer = intent.getStringExtra("idPlayer")

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerDetailPresenter(this, request, gson)
        presenter.getPlayerDetail(idPlayer)

    }

    override fun showPlayerDetail(data: List<Players>) {
        player = Players(data[0].idPlayer,
                data[0].strPlayer,
                data[0].strPosition,
                data[0].strHeight,
                data[0].strWeight,
                data[0].strCutout,
                data[0].strFanart1,
                data[0].strFanart2,
                data[0].strFanart3,
                data[0].strFanart4,
                data[0].strDescriptionEN)

        if(!data[0].strFanart1.equals("")){
            fanArt=data[0].strFanart1.toString()
        }else if(!data[0].strFanart2.equals("")){
            fanArt=data[0].strFanart2.toString()
        }else if(!data[0].strFanart3.equals("")){
            fanArt=data[0].strFanart3.toString()
        }else if(!data[0].strFanart4.equals("")){
            fanArt=data[0].strFanart4.toString()
        }else{
            fanArt=data[0].strThumb.toString()
        }

        Picasso.get().load(fanArt).into(strThumbTxt)
        strWeightTxt.text = if (data[0].strWeight.equals("")) "-" else data[0].strWeight
        strHeightTxt.text = if (data[0].strHeight.equals("")) "-" else data[0].strHeight
        strPositionTxt.text = if (data[0].strPosition.equals("")) "-" else data[0].strPosition
        strDescriptionENTxt.text = if (data[0].strDescriptionEN.equals("")) "No Description" else data[0].strDescriptionEN
        supportActionBar?.title = data[0].strPlayer
    }
}
