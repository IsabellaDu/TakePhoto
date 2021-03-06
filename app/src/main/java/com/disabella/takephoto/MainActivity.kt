package com.disabella.takephoto

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import java.io.File
import java.lang.IllegalArgumentException
import java.net.URL
import java.util.*

const val URL = "https://stickerzone.shop/wp-content/uploads/2018/06/simons-cat-15.png"
lateinit var viewPager: ViewPager2
lateinit var bottomNavigation: BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        viewPager.adapter = ViewPagerAdapter(this)
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigation.selectedItemId = when (position) {
                    0 -> R.id.page1
                    1 -> R.id.page2
                    else -> throw IllegalArgumentException("Only 2 tabs")
                }
            }
        })

        bottomNavigation.setOnItemSelectedListener {
            return@setOnItemSelectedListener when (it.itemId) {
                R.id.page1 -> {
                    viewPager.currentItem = 0
                    true
                }
                R.id.page2 -> {
                    viewPager.currentItem = 1
                    true
                }
                else -> false
            }
        }
    }
}

class ViewPagerAdapter(activity: MainActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Tab1Fragment()
            1 -> Tab2Fragment()
            else -> throw IllegalArgumentException("Only 2 tabs")
        }
    }
}

