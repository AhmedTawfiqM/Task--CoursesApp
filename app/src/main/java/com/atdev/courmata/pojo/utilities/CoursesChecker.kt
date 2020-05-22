package com.atdev.courmata.pojo.utilities

import android.util.Log
import com.atdev.courmata.pojo.model.Course

object CoursesChecker {

    fun checkUdemyCourse(typeCourses: Int, allCourses: List<Course>): ArrayList<Course> {

        val udemyCourse = arrayListOf<Course>()
        for (course in allCourses) {
            if (course.type == typeCourses) //Udemy Code =1
            {
                udemyCourse.add(course)
                Log.d("udemy::", course.toString())
            }
        }
        return udemyCourse
    }
}