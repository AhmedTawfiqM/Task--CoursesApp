package com.atdev.courmata.pojo.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.atdev.courmata.R
import com.atdev.courmata.pojo.enums.SourceEnum
import com.atdev.courmata.pojo.model.Course
import com.atdev.courmata.pojo.utilities.Constants
import com.atdev.courmata.pojo.utilities.CourseListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception


class CustomCoursesAdapter(
    val mContext: Context,
    val courses: ArrayList<Course>,
    val onCourseListner: CourseListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false
    private var isAllItemsLoaded = false

    var sourcesEnum: Array<SourceEnum>
    var colorsSource: Array<String>

    init {
        sourcesEnum = SourceEnum.values()  //.clone()
        colorsSource = mContext.resources.getStringArray(R.array.colors)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = ViewHolderC(
            LayoutInflater.from(mContext).inflate(
                R.layout.single_course,
                parent,
                false
            ), onCourseListner
        )
        when (viewType) {
            VIEW_TYPE_NORMAL -> return view
            VIEW_TYPE_LOADING -> return ProgressHolder(
                LayoutInflater.from(mContext).inflate(
                    R.layout.item_loading,
                    parent,
                    false
                )
            )
            else -> return view
        }

    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            VIEW_TYPE_NORMAL -> {
                holder as ViewHolderC
                loadCourseImage(courses[position].imgURL, holder.ivCourse)
                //
                holder.tvCourseInstructor.text = getItem(position).instructor
                holder.tvSource.text =
                    sourcesEnum[getItem(position).source - 1].name//sources start from backend from 1,2..etc
                holder.tvSource.setBackgroundColor(Color.parseColor(colorsSource[getItem(position).source - 1]))
                holder.tvCourseName.text = getItem(position).name
                holder.tvCost.text = getItem(position).price + " $ "
                //Format Date...
                val date = getItem(position).endDate
                if (date != null && !date.isEmpty() && !date.equals("null") && getItem(position).type != Constants.DowNLOAD) {
                    val endDate = date.substring(0, date.indexOf("T"))
                    val time = date.substring(date.indexOf("T") + 1, date.indexOf("T") + 6)
                    holder.tvDateFinished.text = "End : $endDate / $time"
                }
                //set Color of Sources
                //sources start from backend from 1,2..etc
                val posSource = sourcesEnum[getItem(position).source - 1].ordinal
                holder.tvSource.setBackgroundColor(Color.parseColor(colorsSource[posSource]))
            }
            VIEW_TYPE_LOADING -> {
                holder as ProgressHolder
                if (isAllItemsLoaded) holder.progressBar.visibility = View.GONE
                else holder.progressBar.visibility = View.VISIBLE
            }
            else -> holder as ViewHolderC
        }

    }

    fun setisAllItemsLoaded(isDOne: Boolean) {
        this.isAllItemsLoaded = isDOne
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoaderVisible)
            return if (position == courses.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        else
            return VIEW_TYPE_NORMAL
    }

    fun addItems(postItems: ArrayList<Course>) {
        courses.addAll(postItems)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        courses.add(Course())
        notifyItemInserted(courses.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = courses.size - 1
        val item: Course = getItem(position)
        if (item != null) {
            courses.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun clear() {
        courses.clear()
        notifyDataSetChanged()
    }

    fun getItem(position: Int): Course {
        return courses[position]
    }

}

class ProgressHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var progressBar: ProgressBar

    init {
        progressBar = itemView.findViewById(R.id.progressBar)
    }
}

private fun loadCourseImage(url: String, ivCourse: ImageView) {

    Picasso
        .get()
        .load(url)
        .noFade()
        .into(ivCourse, object : Callback {
            override fun onSuccess() {


            }

            override fun onError(e: Exception?) {
                ivCourse.setImageResource(R.drawable.ic_launcher_background)
            }
        })
}

class ViewHolderC(itemView: View, onCourseListner: CourseListener) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var cardCourse: CardView
    var ivCourse: ImageView
    var tvSource: TextView
    var tvCourseName: TextView
    var tvCourseInstructor: TextView
    var tvDateFinished: TextView
    var onCourseListner: CourseListener
    var tvCost: TextView

    init {
        cardCourse = itemView.findViewById(R.id.cardCourse)
        ivCourse = itemView.findViewById(R.id.ivCourse)
        tvSource = itemView.findViewById(R.id.tvSource)
        tvCourseName = itemView.findViewById(R.id.tvCourseName)
        tvCost = itemView.findViewById(R.id.tvCost)
        tvCourseInstructor = itemView.findViewById(R.id.tvCourseInstructor)
        tvDateFinished = itemView.findViewById(R.id.tvDateFinished)
        this.onCourseListner = onCourseListner
        itemView.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        onCourseListner.onCourseCLick(adapterPosition)
    }
}