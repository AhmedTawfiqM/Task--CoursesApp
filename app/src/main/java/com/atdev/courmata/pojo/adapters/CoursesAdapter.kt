package com.atdev.courmata.pojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.atdev.courmata.R
import com.atdev.courmata.pojo.enums.SourceEnum
import com.atdev.courmata.pojo.model.Course
import com.squareup.picasso.Picasso

class CoursesAdapter(
    val mContext: Context,
    diffCallback: DiffUtil.ItemCallback<Course>
) : PagedListAdapter<Course, ViewHolder>(diffCallback) {

    var sourcesEnum: Array<SourceEnum>

    init {
        sourcesEnum = SourceEnum.values()  //.clone()
//        if (itemCount <= 0)
//            Utils.showToast(mContext, "No Courses Available")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.single_course, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvSource.text = getItem(position)!!.name
        holder.tvCourseInstructor.text = getItem(position)!!.instructor
        holder.tvDateFinished.text = getItem(position)!!.endDate
        holder.tvSource.text =
            sourcesEnum[getItem(position)!!.source - 1].name//sources start from backend from 1,2..etc
        //Load Images
        loadCourseImage(getItem(position)!!.imgURL, holder.ivCourse)
    }

    private fun loadCourseImage(url: String, ivCourse: ImageView) {

        Picasso
            .get()
            .load(url)
            .noFade()
            .into(ivCourse)
    }

}

//


open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var cardCourse: CardView
    var ivCourse: ImageView
    var tvSource: TextView
    var tvCourseName: TextView
    var tvCourseInstructor: TextView
    var tvDateFinished: TextView

    init {
        cardCourse = itemView.findViewById(R.id.cardCourse)
        ivCourse = itemView.findViewById(R.id.ivCourse)
        tvSource = itemView.findViewById(R.id.tvSource)
        tvCourseName = itemView.findViewById(R.id.tvCourseName)
        tvCourseInstructor = itemView.findViewById(R.id.tvCourseInstructor)
        tvDateFinished = itemView.findViewById(R.id.tvDateFinished)
    }
}