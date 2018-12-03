package com.example.rikis.footballmatchscheduleapp


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rikis.footballmatchscheduleapp.utils.MyViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_frag_favorites.*


class FragFavorites : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_frag_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = MyViewPagerAdapter(childFragmentManager)

        val mFragmentFavEvents= FragFavoriteEvent()
        val mFragmentFavTeams= FragFavoriteTeams()


        adapter.addFragment(mFragmentFavEvents,"MATCHES")
        adapter.addFragment(mFragmentFavTeams,"TEAMS")

        viewPagerFav.adapter = adapter
        tabsFav.setupWithViewPager(viewPagerFav)

    }

}
