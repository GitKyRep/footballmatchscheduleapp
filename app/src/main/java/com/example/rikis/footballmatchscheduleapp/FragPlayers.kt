package com.example.rikis.footballmatchscheduleapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.adapter.PlayersAdapter
import com.example.rikis.footballmatchscheduleapp.api.ApiRepository
import com.example.rikis.footballmatchscheduleapp.data.Players
import com.example.rikis.footballmatchscheduleapp.interfacedir.PlayersView
import com.example.rikis.footballmatchscheduleapp.presenter.PlayersPresenter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_frag_players.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor


class FragPlayers : Fragment(),PlayersView, AnkoLogger{

    private var players: MutableList<Players> = mutableListOf()
    private lateinit var presenter: PlayersPresenter
    private lateinit var adapterPlayer: PlayersAdapter
    private lateinit var idTeam: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_players, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        idTeam = arguments!!.getString("idTeam")

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayersPresenter(this, request, gson)

        adapterPlayer = PlayersAdapter(ctx, players) {
            startActivity(intentFor<PlayerDetailActivity>("idPlayer" to "${it.idPlayer}").singleTop())
        }

        val lp = LinearLayoutManager(ctx)
        lp.orientation = LinearLayoutManager.VERTICAL
        listPlayers.layoutManager = lp


        presenter.getPlayersList(idTeam)
        Log.e("Id team",idTeam)
        listPlayers.adapter = adapterPlayer

    }

    override fun showPlayersList(data: List<Players>) {
        players.clear()
        players.addAll(data)
        adapterPlayer.notifyDataSetChanged()
    }


}
