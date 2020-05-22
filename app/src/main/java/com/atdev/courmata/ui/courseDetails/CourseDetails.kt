package com.atdev.courmata.ui.courseDetails

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.atdev.courmata.R
import com.atdev.courmata.pojo.enums.SourceEnum
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.utilities.Constants
import com.atdev.courmata.pojo.utilities.Utils.loadCourseImage
import com.atdev.courmata.pojo.utilities.ads_loader.AdsLoader
import com.atdev.courmata.pojo.utilities.ads_loader.LoadAllAds
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_course_details.*

class CourseDetails : AppCompatActivity() {

    lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_details)

        setDataCourse()
        //
  init()
        //
        AdsLoader.LoadAdaptiveBannerAds(this@CourseDetails, banner_frame)
        showFullAds()
        WatchCourseListner()
    }

    private fun init() {
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
    }

    private fun WatchCourseListner() {
        WatchCourse.setOnClickListener {
            startActivity(Intent(this, com.atdev.courmata.ui.watchcourse.WatchCourse::class.java)
                .putExtra("url",course.courseURL))
        }
    }

    private fun showFullAds() {

        //Value Of Ads for Interitial Screen
        Constants.ADS_SHOWN_COUNTER++
        Log.d("AdsTEst", Constants.ADS_SHOWN_COUNTER.toString() + "")
        if (Constants.ADS_SHOWN_COUNTER % 2 == 0) {
            // Load Ads Full Screen
            LoadAllAds.LoadFullScreenAds(applicationContext)
            //Value Of Ads for Interitial Screen
            Log.d("AdsTEst", Constants.ADS_SHOWN_COUNTER.toString() + "")
        }
    }

    private fun setDataCourse() {

         course = Gson().fromJson(intent.getStringExtra("c")!!, Course::class.java)
        //
        tvCourseName.text = course.name
        tvCourseInstructor.text = course.instructor
        tvCourseDescription.text = course.description
        tvCost.text = "${course.price}$"
        if (course.endDate != null && course.type != Constants.DowNLOAD)
            tvDateFinished.text = course.endDate

        loadCourseImage(course.imgURL, ivCourse)

        val colorsSource = resources.getStringArray(R.array.colors)
        tvSource.text = SourceEnum.values()[course.source - 1].name
        tvSource.setBackgroundColor(Color.parseColor(colorsSource[course.source - 1]))

        //
        supportActionBar?.title = course.name
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

}
