package com.example.rikis.footballmatchscheduleapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.R
import com.example.rikis.footballmatchscheduleapp.data.FavoritesEvent
import com.example.rikis.footballmatchscheduleapp.utils.ellipsize
import com.example.rikis.footballmatchscheduleapp.utils.getLongDate
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*


class FavoriteEventAdapter(private val context: Context, private val favorite: List<FavoritesEvent>, private val listner: (FavoritesEvent) -> Unit)
    : RecyclerView.Adapter<FavoriteEventAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(favorite[position], listner)
    }

    /*override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int)=
            RecyclerViewAdapter.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))*/

    override fun getItemCount(): Int = favorite.size

    //Menerapkan Kotlin Android Extensions (LayoutContainer)
    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(favorite: FavoritesEvent, listner: (FavoritesEvent) -> Unit) {
            //Menerapkan Kotlin Android Extensions (View)

            val tanggalEvent = getLongDate(favorite.dateEvent)
            val home = ellipsize(favorite.homeTeam, 14)
            val away = ellipsize(favorite.awayTeam, 14)

            strHomeTeam.text = home
            strAwayTeam.text = away
            dateEvent.text = tanggalEvent
            intHomeScore.text = favorite.homeScore
            intAwayScore.text = favorite.awayScore

            containerView.setOnClickListener {
                listner(favorite)
            }
        }
    }
}