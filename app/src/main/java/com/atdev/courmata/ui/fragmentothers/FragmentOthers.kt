package com.atdev.courmata.ui.fragmentothers

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.atdev.courmata.R
import com.atdev.courmata.pojo.adapters.CustomCoursesAdapter
import com.atdev.courmata.pojo.adapters.PaginationListener
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.repos.GetCourses
import com.atdev.courmata.pojo.utilities.Constants
import com.atdev.courmata.pojo.utilities.Constants.DowNLOAD
import com.atdev.courmata.pojo.utilities.Constants.TIME_CHUNKS
import com.atdev.courmata.pojo.utilities.CourseListener
import com.atdev.courmata.pojo.utilities.CoursesChecker
import com.atdev.courmata.pojo.utilities.Utils
import com.atdev.courmata.ui.courseDetails.CourseDetails
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fragment_others.*
import kotlinx.android.synthetic.main.no_internet_connection.*

class FragmentOthers : Fragment(), SwipeRefreshLayout.OnRefreshListener, CourseListener {

    lateinit var viewModel: DownloadViewModel
    var coursesAll: ArrayList<Course> = ArrayList()
    var adapterCourse: CustomCoursesAdapter? = null
    private var currentPage: Int = PaginationListener.PAGE_START
    private var isLastPageM = false
    private val totalPage = 10
    private val totalRequestedPage = 9  //In every Page
    private var isLoadingM = false
    var itemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_others, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rvCourses.setHasFixedSize(true)
        rvCourses.layoutManager = layoutManager
        adapterCourse = CustomCoursesAdapter(activity!!, ArrayList(), this)
        //
        swipeRefresh!!.setOnRefreshListener(this)
        //
        callServerforCourses()
        resfreshbtnListner()

        rvCourses.addOnScrollListener(object : PaginationListener(layoutManager) {
            override fun isLastPage(): Boolean {
                return isLastPageM
            }

            override fun loadMoreItems() {
                isLoadingM = true
                currentPage++
                doApiCall()
            }

            override fun isLoading(): Boolean {
                return isLoadingM
            }

        })
    }

    private fun callServerforCourses() {

        //
        if (Utils.isInternetAvailable(activity!!)) {
            noInternet.visibility = View.GONE
            swipeRefresh.visibility = View.VISIBLE
            if (progressOthers != null) progressOthers.visibility = View.VISIBLE

            GetCourses.getCourses().observe(activity!!, Observer {

                rvCourses.adapter = adapterCourse
                //
                val mCourses = CoursesChecker.checkUdemyCourse(DowNLOAD, it)
                coursesAll.addAll(mCourses)
                if (mCourses.size <= 0) {
                    Toast.makeText(activity!!, "", Toast.LENGTH_LONG).show()
                    if (progressOthers != null) progressOthers.visibility = View.GONE
                } else doApiCall()

            })
        } else {
            noInternet.visibility = View.VISIBLE
            swipeRefresh.visibility = View.GONE
            if (progressOthers != null) progressOthers.visibility = View.GONE
        }

    }

    private fun resfreshbtnListner() {

        btnResfresh.setOnClickListener {
            if (Utils.isInternetAvailable(activity!!))
                callServerforCourses()
            else
                Toast.makeText(
                    activity!!,
                    "Still, No Internet Connection",
                    Toast.LENGTH_LONG
                ).show()
        }
    }


    private fun doApiCall() {

        if (coursesAll.size <= 0) {
            Toast.makeText(
                activity!!,
                "No Udemy Courses Existed This Time",
                Toast.LENGTH_LONG
            ).show()
        }
        var isAllItemsLoaded = false
        val items: ArrayList<Course> = ArrayList()
        Handler().postDelayed({
            for (i in 0..totalRequestedPage) {
                if (itemCount < coursesAll.size) {
                    items.add(coursesAll[itemCount])
                    itemCount++
                } else {
                    isAllItemsLoaded = true
                }
            }

            adapterCourse!!.setisAllItemsLoaded(isAllItemsLoaded)
            if (currentPage != PaginationListener.PAGE_START) adapterCourse!!.removeLoading()
            adapterCourse!!.addItems(items)
            if (swipeRefresh != null) swipeRefresh!!.isRefreshing = false
            // check weather is last page or not
            if (currentPage < totalPage) {
                adapterCourse!!.addLoading()
            } else {
                isLastPageM = true
            }
            isLoadingM = false
            if (progressOthers != null) progressOthers.visibility = View.GONE
        }, TIME_CHUNKS)

    }

    override fun onRefresh() {

        itemCount = 0
        currentPage = PaginationListener.PAGE_START
        isLastPageM = false
        adapterCourse!!.clear()
        adapterCourse!!.setisAllItemsLoaded(false)
        doApiCall()
    }

    override fun onCourseCLick(position: Int) {

        startActivity(
            Intent(activity!!, CourseDetails::class.java)
                .putExtra("c", Gson().toJson(adapterCourse!!.getItem(position)))
        )
    }

}
