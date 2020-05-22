package com.atdev.courmata.pojo.pagination

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PageKeyedDataSource
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.repos.GetCourses
import com.atdev.courmata.pojo.utilities.Constants
import com.atdev.courmata.pojo.utilities.CoursesChecker

class CoursesDataSource(val mContext: LifecycleOwner, val typeCourses: Int) :
    PageKeyedDataSource<Int, Course>() {

    var coursesCopouns: MutableLiveData<List<Course>> = MutableLiveData<List<Course>>()
    var coursesDownload: MutableLiveData<List<Course>> = MutableLiveData<List<Course>>()

    init {
        getAllCOurses()

    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Course>
    ) {
        coursesCopouns.observe(mContext, Observer {
            callback.onResult(
                it as MutableList<Course>,
                null,
                Constants.FIRST_PAGE + 8
            )
        })
        if (coursesCopouns.value != null)
            coursesCopouns.let {
                callback.onResult(
                    it.value as MutableList<Course>,
                    null,
                    Constants.FIRST_PAGE + 8
                )
            }
//        GetCourses.getCourses().observe(mContext, Observer {
//            //Udemy Copouns> Code =1   //Download Torrent Courses Code =2
//            callback.onResult(
//                it as MutableList<Course>, null, Constants.FIRST_PAGE + 8
//            )
//
//        })

    }//..

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Course>) {

        coursesCopouns.observe(mContext, Observer {
            val key: Int?
            if (params.key == coursesCopouns!!.value!!.size)
                key = params.key + 1
            else
                key = null
            callback.onResult(
                it as MutableList<Course>, key
            )
        })
        if (coursesCopouns.value != null)
            coursesCopouns.let {
                val key: Int?
                if (params.key == coursesCopouns!!.value!!.size)
                    key = params.key + 1
                else
                    key = null
                callback.onResult(
                    it!!.value as MutableList<Course>, key
                )
            }
//        GetCourses.getCourses().observe(mContext, Observer {
//
//            //Udemy Copouns> Code =1   //Download Torrent Courses Code =2
//            callback.onResult(
//                it as MutableList<Course>, key
//            );
//
//        })
    }

    private fun getAllCOurses() {
        GetCourses.getCourses().observe(mContext, Observer {

            //Udemy Copouns> Code =1   //Download Torrent Courses Code =2
            coursesCopouns.value = CoursesChecker.checkUdemyCourse(1, it)
            coursesDownload.value = CoursesChecker.checkUdemyCourse(2, it)

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Course>) {


    }
}