package com.example.rikis.footballmatchscheduleapp


import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.*
import com.example.rikis.footballmatchscheduleapp.utils.MyViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_matches.*
import org.jetbrains.anko.singleTop
import org.jetbrains.anko.support.v4.intentFor


class Matches : Fragment() {

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search_btn -> {
                startActivity(intentFor<SearchMatches>().singleTop())
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val adapter = MyViewPagerAdapter(childFragmentManager)

        val mFragmentLast = FragLastSchedule()
        val mFragmentNext= FragNextSchedule()

        adapter.addFragment(mFragmentLast,"Last Schedule")
        adapter.addFragment(mFragmentNext,"Next Schedule")

        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

    }

}
