package com.example.rikis.footballmatchscheduleapp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.R
import com.example.rikis.footballmatchscheduleapp.data.Event
import com.example.rikis.footballmatchscheduleapp.utils.ellipsize
import com.example.rikis.footballmatchscheduleapp.utils.getLongDate
import com.example.rikis.footballmatchscheduleapp.utils.getStrDate
import com.example.rikis.footballmatchscheduleapp.utils.toGMTFormat
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list.*
import java.text.SimpleDateFormat


class RecyclerViewAdapter(private val context: Context, private val items: List<Event>, private val listner: (Event) -> Unit)
    : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(items[position], listner)
    }

    override fun getItemCount(): Int = items.size

    //Menerapkan Kotlin Android Extensions (LayoutContainer)
    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindItem(events: Event, listner: (Event) -> Unit) {
            //Menerapkan Kotlin Android Extensions (View)

            val tanggalEvent = getLongDate(events.dateEvent)
            val tglEvent = getStrDate(events.dateEvent.toString())
            val strTime = events.strTime.toString()
            val waktuEvent :String

            if(!strTime.equals("null")){
                waktuEvent = SimpleDateFormat("HH:mm").format(toGMTFormat(tglEvent,strTime));
            }else{
                waktuEvent="-"
            }
            //Log.e("TAG",tglEvent+" = "+strTime)
            //waktuEvent = strTime
            //Log.e("Waktu event",waktuEvent);
            val home = ellipsize(events.strHomeTeam, 14)
            val away = ellipsize(events.strAwayTeam, 14)

            strHomeTeam.text = home
            strAwayTeam.text = away
            dateEvent.text = tanggalEvent
            timeEvent.text = waktuEvent
            intHomeScore.text = events.intHomeScore
            intAwayScore.text = events.intAwayScore

            containerView.setOnClickListener {
                listner(events)
            }

            /*strNotif.setOnClickListener {
                val intent = Intent(Intent.ACTION_INSERT).apply {
                    data = CalendarContract.Events.CONTENT_URI
                    setType("vnd.android.cursor.item/event");
                    putExtra(CalendarContract.Events.TITLE, "tes")
                    putExtra(CalendarContract.Events.EVENT_LOCATION, "tes")
                    putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, "2015-02-02")
                    putExtra(CalendarContract.EXTRA_EVENT_END_TIME, "2015-02-02")
                }
                startActivity(intent)
            }*/
        }
    }
}