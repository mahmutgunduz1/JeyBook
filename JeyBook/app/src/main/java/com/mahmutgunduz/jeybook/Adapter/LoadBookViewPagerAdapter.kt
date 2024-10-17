package com.mahmutgunduz.jeybook.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahmutgunduz.jeybook.Fragment.LoadBookFragments.CurrentlyLoadFragment
import com.mahmutgunduz.jeybook.Fragment.LoadBookFragments.ReadBooksLoadFragment
import com.mahmutgunduz.jeybook.Fragment.LoadBookFragments.WantToReadBooksLoadFragment


class LoadBookViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity){
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentlyLoadFragment()  // Şu an okunanlar
            1 -> ReadBooksLoadFragment()       // Okunanlar
            2 -> WantToReadBooksLoadFragment ()      // Okumak istediklerim
            else -> CurrentlyLoadFragment()
        }
    }


}