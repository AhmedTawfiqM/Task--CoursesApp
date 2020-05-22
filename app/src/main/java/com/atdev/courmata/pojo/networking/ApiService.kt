package com.ebada.embassy.pojo.networking

import com.atdev.courmata.pojo.model.Course
import retrofit2.http.GET

interface ApiService {

//https://freecourses.azurewebsites.net/api/courses
    @GET("courses")
    suspend fun getCourses(): List<Course>
}