package com.atdev.courmata.ui.mainactivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.atdev.courmata.R
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.repos.GetCourses
import com.atdev.courmata.pojo.utilities.CoursesChecker
import com.atdev.courmata.pojo.utilities.SharedPrefs
import com.atdev.courmata.pojo.utilities.SharedPrefs.ISUSER_FirstTIME
import com.atdev.courmata.pojo.utilities.ads_loader.AdsLoader
import com.atdev.courmata.ui.aboutus.AboutUs
import com.atdev.courmata.ui.fragmentothers.FragmentOthers
import com.atdev.courmata.ui.fragmentudemy.FragmentUdemy
import com.atdev.courmata.ui.onboardingscreen.OnBoardingScrenn
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.initialization.InitializationStatus
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener
import com.google.android.material.bottomnavigation.LabelVisibilityMode
import kotlinx.android.synthetic.main.activity_main.*

//https://androidwave.com/pagination-in-recyclerview/
//https://proandroiddev.com/8-steps-to-implement-paging-library-in-android-d02500f7fffe
class MainActivity : AppCompatActivity() {

    var coursesCopouns: MutableLiveData<List<Course>>? = null
    var coursesDownload: MutableLiveData<List<Course>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //check if First time USer
        isUserFirstTime()
        setContentView(R.layout.activity_main)

        //setup UI
        setUpBottomNavigationListner()
        setUpNavigationView()
        // Begin EXPLORE Page fragment
        navigatetoFragment(FragmentUdemy())
        initMenuDrawer()
        //getAllCourses()
        AdsLoader.addTestDeviceAds()
    }

    //start On Boarding Screen
    fun openOnBoardingScreen() {
        startActivity(
            Intent(this, OnBoardingScrenn::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        finish()
    }

    //check if First time USer
    private fun isUserFirstTime() {
        if (!SharedPrefs.isBooleanData(this, ISUSER_FirstTIME)
        ) { // False mean First Time User
            openOnBoardingScreen()
            SharedPrefs.setDataBoolean(this, ISUSER_FirstTIME, true)
        }
    }

    // Customize Menu Drawer
    private fun initMenuDrawer() {
        iv_Menu.setOnClickListener { drawerLayout.openDrawer(Gravity.LEFT) }
        //RTL
        //window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    fun getAllCourses() {

        GetCourses.getCourses().observe(this, Observer {

            //Udemy Copouns> Code =1   //Download Torrent Courses Code =2
            coursesCopouns!!.value = CoursesChecker.checkUdemyCourse(1, it)
            coursesDownload!!.value = CoursesChecker.checkUdemyCourse(2, it)

        })
    }

    private fun setUpBottomNavigationListner() {

        bottomNavigation.labelVisibilityMode = LabelVisibilityMode.LABEL_VISIBILITY_LABELED
        bottomNavigation.selectedItemId = R.id.itemExplore
        //
        bottomNavigation.setOnNavigationItemSelectedListener {

            when (it.itemId) {
                R.id.itemExplore -> navigatetoFragment(FragmentUdemy())
                R.id.itemMyTrips -> navigatetoFragment(FragmentOthers());
                else -> print("")//openDialog_AddDailyData()
            }
            true
        }
    }

    private fun setUpNavigationView() {

        navigationviuew.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemHelp -> startActivity(
                    Intent(
                        applicationContext,
                        OnBoardingScrenn::class.java
                    )
                )
                R.id.itemPrivacy -> {
                    val browserIntent =
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://sites.google.com/view/courmata/home")
                        );
                    startActivity(browserIntent)
                }
                R.id.itemAboutUs -> {
                    startActivity(Intent(this, AboutUs::class.java))
                }

            }
            drawerLayout.closeDrawers()
            true
        }
    }

    private fun navigatetoFragment(mainPage: Fragment) {

        //..
        supportFragmentManager.beginTransaction().replace(
            R.id.fragemntContainer,
            mainPage, "Tag"
        )
            .commit()
        //..

    }

}
