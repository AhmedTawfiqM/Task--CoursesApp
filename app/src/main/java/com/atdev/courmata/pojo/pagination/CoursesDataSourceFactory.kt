package com.atdev.courmata.pojo.pagination

import androidx.lifecycle.LifecycleOwner
import androidx.paging.DataSource
import com.atdev.courmata.pojo.model.Course

class CoursesDataSourceFactory(val mContext: LifecycleOwner, val typeCourses: Int) :
    DataSource.Factory<Int, Course>() {

    override fun create(): DataSource<Int, Course> {

        return CoursesDataSource(mContext, typeCourses)

    }

}