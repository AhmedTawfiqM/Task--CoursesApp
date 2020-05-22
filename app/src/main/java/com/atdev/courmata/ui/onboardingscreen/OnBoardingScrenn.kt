package com.atdev.courmata.ui.onboardingscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.atdev.courmata.R
import com.atdev.courmata.pojo.adapters.AdapterBoardingScreen
import com.atdev.courmata.ui.mainactivity.MainActivity
import kotlinx.android.synthetic.main.activity_on_boarding_screnn.*

class OnBoardingScrenn : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screnn)

        initIndicator()
        gettingStartListner()
    }

    private fun gettingStartListner() {

        btnLogin.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    // pointes for slider
    private fun initIndicator() {
        viewPager.adapter = AdapterBoardingScreen(this)

        pointes!!.setViewPager(viewPager)
        val density = resources.displayMetrics.density
        pointes!!.radius = 7 * density
        pointes!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {}
            override fun onPageScrolled(pos: Int, arg1: Float, arg2: Int) {}
            override fun onPageScrollStateChanged(pos: Int) {}
        })
    }
}
