package com.mahmutgunduz.jeybook.Adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.SaveFragment
import com.mahmutgunduz.jeybook.Fragment.SaveTabFragments.CurrentlyReadingFragment
import com.mahmutgunduz.jeybook.Fragment.SaveTabFragments.FavoriteBooksFragment
import com.mahmutgunduz.jeybook.Fragment.SaveTabFragments.ReadBooksFragment
import com.mahmutgunduz.jeybook.Fragment.SaveTabFragments.WantToReadFragment

class SaveViewPagerAdapter(fragmentActivity: SaveFragment) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
       return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CurrentlyReadingFragment()  // Şu an okunanlar
            1 -> ReadBooksFragment()         // Okunanlar
            2 -> WantToReadFragment()        // Okumak istediklerim
            3 -> FavoriteBooksFragment()     // Favori kitaplar
            else -> CurrentlyReadingFragment()
        }
    }
}