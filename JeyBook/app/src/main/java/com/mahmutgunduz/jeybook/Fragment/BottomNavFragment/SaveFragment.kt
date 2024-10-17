package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmutgunduz.jeybook.Adapter.SaveViewPagerAdapter
import com.mahmutgunduz.jeybook.R


class SaveFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_save, container, false)

        // ViewPager ve TabLayout bileşenlerine XML'den erişiyoruz
       viewPager = view.findViewById(R.id.viewPager)
        tabLayout = view.findViewById(R.id.tabLayout)

        // okunan
        // okuduklarim
        // okumak istediklerim.


        //favori kitaplarim





        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // ViewPager ve Adapteri bağla
        val adapter = SaveViewPagerAdapter(this@SaveFragment)
        viewPager.adapter = adapter


        // TabLayout ve ViewPager2'yi birbirine bağla
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Şu Anda Okunanlar" // Currently Reading
                1 -> "Okunan Kitaplar"    // Read Books
                2 -> "Okumak İstediğim Kitaplar" // Want to Read
                3 -> "Favori Kitaplar"    // Favorite Books
                else -> "Şu Anda Okunanlar" // Currently Reading

            }
        }.attach()
    }


}