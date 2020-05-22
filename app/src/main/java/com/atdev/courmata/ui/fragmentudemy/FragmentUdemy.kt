package com.atdev.courmata.ui.fragmentudemy


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.atdev.courmata.R
import com.atdev.courmata.pojo.adapters.CustomCoursesAdapter
import com.atdev.courmata.pojo.adapters.PaginationListener
import com.atdev.courmata.pojo.adapters.PaginationListener.PAGE_START
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.repos.GetCourses
import com.atdev.courmata.pojo.utilities.Constants.TIME_CHUNKS
import com.atdev.courmata.pojo.utilities.Constants.Udemy_COPOUNS
import com.atdev.courmata.pojo.utilities.CourseListener
import com.atdev.courmata.pojo.utilities.CoursesChecker
import com.atdev.courmata.pojo.utilities.Utils
import com.atdev.courmata.ui.courseDetails.CourseDetails
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_fragment_udemy.*
import kotlinx.android.synthetic.main.no_internet_connection.*

//Paging Example
//https://github.com/AhmedTawfiqM/Movies-APp
class FragmentUdemy : Fragment(), SwipeRefreshLayout.OnRefreshListener, CourseListener {

    lateinit var viewModel: UdemyViewModel
    var coursesAll: ArrayList<Course> = ArrayList()
    var adapterCourse: CustomCoursesAdapter? = null
    private var currentPage: Int = PAGE_START
    private var isLastPageM = false
    private val totalPage = 10
    private val totalRequestedPage = 9  //In every Page
    private var isLoadingM = false
    var itemCount = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fragment_udemy, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

      onScrollListner()
    }

    private fun onScrollListner() {

        val layoutManager = LinearLayoutManager(activity!!, LinearLayoutManager.VERTICAL, false)
        rvCourses.setHasFixedSize(true)
        rvCourses.layoutManager = layoutManager
        adapterCourse = CustomCoursesAdapter(activity!!, ArrayList(), this)
        //
        swipeRefresh!!.setOnRefreshListener(this)
        //get course from Server
        callServerforCourses()
        //add listner
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

    private fun callServerforCourses() {

        //
        if (Utils.isInternetAvailable(activity!!)) {
            noInternet.visibility = View.GONE
            swipeRefresh.visibility = View.VISIBLE
            if (progressUdemy != null) progressUdemy.visibility = View.VISIBLE

            GetCourses.getCourses().observe(activity!!, Observer {

                rvCourses.adapter = adapterCourse
                //
                val mCourses = CoursesChecker.checkUdemyCourse(Udemy_COPOUNS, it)
                coursesAll.addAll(mCourses)
                if (mCourses.size <= 0) {
                    Toast.makeText(
                        activity!!,
                        "No Udemy Courses Existed This Time",
                        Toast.LENGTH_LONG
                    ).show()
                    if (progressUdemy != null) progressUdemy.visibility = View.GONE
                } else doApiCall()

            })
        } else {
            noInternet.visibility = View.VISIBLE
            swipeRefresh.visibility = View.GONE
            if (progressUdemy != null) progressUdemy.visibility = View.GONE
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
            if (currentPage != PAGE_START) adapterCourse!!.removeLoading()
            adapterCourse!!.addItems(items)
            if (swipeRefresh != null) swipeRefresh!!.isRefreshing = false
            // check weather is last page or not
            if (currentPage < totalPage) {
                adapterCourse!!.addLoading()
            } else {
                isLastPageM = true
            }
            isLoadingM = false
            if (progressUdemy != null) progressUdemy.visibility = View.GONE
        }, TIME_CHUNKS)
    }


    fun getCallBack(): DiffUtil.ItemCallback<Course> {

        return object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldMovie: Course, newMovie: Course): Boolean {
                return oldMovie.id.equals(newMovie.id)
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldMovie: Course, newMovie: Course): Boolean {
                return oldMovie.equals(newMovie)
            }
        }
    }


    override fun onRefresh() {

        itemCount = 0
        currentPage = PAGE_START
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
