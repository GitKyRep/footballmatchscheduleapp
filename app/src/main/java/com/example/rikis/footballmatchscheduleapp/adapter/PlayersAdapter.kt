package com.example.rikis.footballmatchscheduleapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.R
import com.example.rikis.footballmatchscheduleapp.data.Players
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_players.*

class PlayersAdapter(private val context: Context, private val items: List<Players>, private val listner: (Players) -> Unit)
    : RecyclerView.Adapter<PlayersAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list_players, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listner)
    }

    override fun getItemCount(): Int = items.size

    //Menerapkan Kotlin Android Extensions (LayoutContainer)
    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(players: Players, listner: (Players) -> Unit) {
            //Menerapkan Kotlin Android Extensions (View)

            if(players.strCutout.equals(null)){
                Picasso.get().load(R.drawable.player_avatar).into(strCutout)
            }else{
                Picasso.get().load(players.strCutout).into(strCutout)
            }

            strPlayer.text = players.strPlayer
            strPosition.text = players.strPosition

            containerView.setOnClickListener {
                listner(players)
            }
        }
    }
}