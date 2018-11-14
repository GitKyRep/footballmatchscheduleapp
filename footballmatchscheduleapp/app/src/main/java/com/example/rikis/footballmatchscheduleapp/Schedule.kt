package com.example.rikis.footballmatchscheduleapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlinx.android.synthetic.main.activity_schedule.*

class Schedule : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        val adapter = MyViewPagerAdapter(supportFragmentManager)

        val mFragmentLast = FragSchedule()
        val mArgsLast = Bundle()
        mArgsLast.putString("url", "last")
        mFragmentLast.setArguments(mArgsLast)

        val mFragmentNext= FragSchedule()
        val mArgsNext = Bundle()
        mArgsNext.putString("url", "next")
        mFragmentNext.setArguments(mArgsNext)


        adapter.addFragment(mFragmentLast,"Last Schedule")
        adapter.addFragment(mFragmentNext,"Next Schedule")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    class MyViewPagerAdapter (manager: FragmentManager) : FragmentPagerAdapter(manager){
        private val fragmentList : MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()


        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment : Fragment,title:String){
            fragmentList.add(fragment)
            titleList.add(title)
        }
        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }
    }
}
