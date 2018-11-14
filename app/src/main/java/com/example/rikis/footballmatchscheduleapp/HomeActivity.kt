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
                prevMatch -> {
                    loadPrevMatchFragment(savedInstanceState)
                }
                nextMatch -> {
                    loadNextMatchFragment(savedInstanceState)
                }
                favMatch -> {
                    loadFavMatchFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = prevMatch

    }

    private fun loadPrevMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val mFragmentLast = FragSchedule()
            val mArgsLast = Bundle()
            mArgsLast.putString(getString(R.string.url),getString(R.string.last))
            mFragmentLast.arguments = mArgsLast

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentLast, mFragmentLast::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadNextMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val mFragmentNext = FragSchedule()
            val mArgsNext = Bundle()
            mArgsNext.putString(getString(R.string.url),getString(R.string.next))
            mFragmentNext.arguments = mArgsNext

            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, mFragmentNext, mFragmentNext::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FragFavoriteEvent(), FragFavoriteEvent::class.java.simpleName)
                    .commit()
        }
    }
}
