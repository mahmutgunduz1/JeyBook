package com.mahmutgunduz.jeybook.View

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.mahmutgunduz.jeybook.Adapter.LoadBookViewPagerAdapter
import com.mahmutgunduz.jeybook.Adapter.SaveViewPagerAdapter
import com.mahmutgunduz.jeybook.databinding.ActivityBookLoadBinding
import com.squareup.picasso.Picasso

class BookLoadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookLoadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookLoadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val bookTitle = intent.getStringExtra("bookTitle")
        val bookImageUrl = intent.getStringExtra("bookImageUrl")



        binding.txtTittle.text=bookTitle.toString()

        Picasso.get().load(bookImageUrl).into(binding.img)


        // ViewPager ve Adapteri bağla
        val adapter = LoadBookViewPagerAdapter( this)



        binding.viewpager2.adapter=adapter


        // TabLayout ve ViewPager2'yi birbirine bağla
        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            tab.text = when (position) {
                0 -> "Oku" // Currently Reading
                1 -> "Daha once okudum"    // Read Books
                2 -> "Okumak İstediğim Kitaplar" // Want to Read
                3 -> "Favori Kitaplar"    // Favorite Books
                else -> "Şu Anda Okunanlar" // Currently Reading

            }
        }.attach()

    }


}