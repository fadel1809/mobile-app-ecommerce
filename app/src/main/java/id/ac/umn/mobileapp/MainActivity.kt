package id.ac.umn.mobileapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.ac.umn.mobileapp.bag.BagFragment
import id.ac.umn.mobileapp.explore.ExploreFragment
import id.ac.umn.mobileapp.home.HomeFragment
import id.ac.umn.mobileapp.profile.ProfileFragment
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val fragment = when (menuItem.itemId) {
                R.id.bottom_home -> HomeFragment()
                R.id.bottom_Explore -> ExploreFragment()
                R.id.bottom_bag -> BagFragment()
                R.id.bottom_profile -> ProfileFragment()
                else -> HomeFragment()
            }
            replaceFragment(fragment)
            true
        }

        replaceFragment(HomeFragment())
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit()
    }
}