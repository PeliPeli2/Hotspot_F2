package com.example.hotspot_f2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.hotspot_f2.fragments.FavouritesFragment
import com.example.hotspot_f2.fragments.HomeFragment
import com.example.hotspot_f2.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val favouriteFragment = FavouritesFragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragments(homeFragment)

        bottom_navigation.setOnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.ic_home -> makeCurrentFragments(homeFragment)
            R.id.ic_favorite -> makeCurrentFragments(favouriteFragment)
            R.id.ic_settings -> makeCurrentFragments(settingsFragment)

        }
            true
        }
    }


    private fun makeCurrentFragments(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }
}