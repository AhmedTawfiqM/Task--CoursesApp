package com.atdev.courmata.pojo.repos

import android.util.Log
import androidx.lifecycle.LiveData
import com.atdev.courmata.pojo.model.Course
import com.ebada.embassy.pojo.networking.RetrofitBuilder
import kotlinx.coroutines.*
import java.lang.Exception

object GetCourses {

    var job: CompletableJob? = null

    fun getCourses(): LiveData<List<Course>> {
        //..
        job = Job()

        return object : LiveData<List<Course>>() {

            override fun onActive() {
                super.onActive()

                job?.let { thejob ->

                    CoroutineScope(Dispatchers.IO + thejob).launch {

                        val response = RetrofitBuilder.apiService.getCourses()
                        withContext(Dispatchers.Main) {

                            try {
                                value = response
                                thejob.complete()
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                                Log.d("debugL::", ex.message)
                            }
                        }
                    }
                }
            }
        }
    }

    fun cancelJob() {
        job!!.cancel()
    }
}