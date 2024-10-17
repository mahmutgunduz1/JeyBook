package com.mahmutgunduz.jeybook.View

import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.ProfileFragment
import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.DiscoverFragment
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.HomePageFragment

import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.SaveFragment
import com.mahmutgunduz.jeybook.Fragment.BottomNavFragment.SearchFragment
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        replaceFragment(DiscoverFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.homePageFragment -> replaceFragment(HomePageFragment())
                R.id.discover -> replaceFragment(DiscoverFragment())
                R.id.search -> replaceFragment(SearchFragment())
                R.id.save -> replaceFragment(SaveFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else -> {


                }

            }
            true
        }

    }

    override fun onResume() {
        super.onResume()
        val navigateTo = intent.getStringExtra("navigate_to")
        if (navigateTo == "SaveFragment") {
         binding. bottomNavigationView.selectedItemId = R.id.save
        }
    }

  fun replaceFragment(fragment: Fragment) {
        val framentManager = supportFragmentManager
        val fragmentTransaction = framentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)

        fragmentTransaction.commit()


    }

}
