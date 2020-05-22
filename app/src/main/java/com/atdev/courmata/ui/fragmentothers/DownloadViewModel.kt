package com.atdev.courmata.ui.fragmentothers

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.pagination.CoursesDataSourceFactory
import com.atdev.courmata.pojo.utilities.Constants
import java.util.concurrent.Executor

class DownloadViewModel : ViewModel() {

    private var liveData: LiveData<PagedList<Course?>?>? = null
    private var config: PagedList.Config? = null

    init {
        config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(Constants.PAGE_SIZE)
            .build()
    }

    var MainExecutor: Executor = object : Executor {
        var handler: Handler = Handler(Looper.getMainLooper())
        override fun execute(runnable: Runnable?) {
            handler.post(runnable)
        }
    }

    fun getCourses(context: LifecycleOwner): LiveData<PagedList<Course?>?>? {
        if (liveData == null) {
            val coursesDataSource = CoursesDataSourceFactory(context, Constants.DowNLOAD)
            liveData = LivePagedListBuilder(coursesDataSource, config!!)
                .setFetchExecutor(MainExecutor).build()
        }
        return liveData
    }
}