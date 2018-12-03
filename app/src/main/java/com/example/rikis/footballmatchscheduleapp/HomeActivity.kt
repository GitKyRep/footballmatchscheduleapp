package com.example.rikis.footballmatchscheduleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.rikis.footballmatchscheduleapp.R.id.*
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                matches -> {
                    loadMatchFragment(savedInstanceState)
                }
                teams -> {
                    loadTeamFragment(savedInstanceState)
                }
                favMatch -> {
                    loadFavorites(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = matches

    }

    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val mFragmentMatches = Matches()
            val mArgsLast = Bundle()
            mArgsLast.putString(getString(R.string.url),getString(R.string.last))
            mFragmentMatches.arguments = mArgsLast

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentMatches, mFragmentMatches::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeamFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val mFragmentNext = FragTeams()
            val mArgsNext = Bundle()
            mArgsNext.putString(getString(R.string.url),getString(R.string.next))
            mFragmentNext.arguments = mArgsNext

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentNext, mFragmentNext::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavorites(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FragFavorites(), FragFavorites::class.java.simpleName)
                    .commit()
        }
    }
}
