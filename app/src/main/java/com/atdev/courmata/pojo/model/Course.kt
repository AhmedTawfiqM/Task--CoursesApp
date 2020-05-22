package com.atdev.courmata.pojo.model

import com.google.gson.annotations.SerializedName

//[{"id":3,"name":"stringss","instructor":"string","summary":"string","description":"string","imgURL":"string","videoURL":"string","courseURL":"string","price":0,"duration":0,"creationDate":"2020-03-24T14:04:44.3800827","endDate":null,"couponPeriod":0,"source":1,"cateogry":1,"type":1,"isArchived":false}]
data class Course(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("instructor") val instructor: String,
    @SerializedName("summary") val summary: String,
    @SerializedName("description") val description: String,
    @SerializedName("imgURL") val imgURL: String,
    @SerializedName("videoURL") val videoURL: String,
    @SerializedName("courseURL") val courseURL: String,
    @SerializedName("price") val price: String,
    @SerializedName("duration") val duration: String,
    @SerializedName("creationDate") val creationDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("couponPeriod") val couponPeriod: Int,
    @SerializedName("source") val source: Int,
    @SerializedName("cateogry") val cateogry: Int,
    @SerializedName("type") val type: Int,
    @SerializedName("isArchived") val isArchived: Boolean
) {

    constructor() : this(0, "", "", "", "", "", "", "", "", "", "", "", 1, 1, 1, 1, false)
}