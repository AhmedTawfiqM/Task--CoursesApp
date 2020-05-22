package com.atdev.courmata.pojo.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.atdev.courmata.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.single_onboarding.view.*
import java.lang.Exception

class AdapterBoardingScreen(val context: Context) : PagerAdapter() {

    var  arImages: Array<Int>
    var  arTitles: Array<String>
    var  arDescs: Array<String>
    init {
        arImages = arrayOf(R.drawable.coupon,R.drawable.coupon,R.drawable.coupon)
        arTitles = context.resources.getStringArray(R.array.titles)
        arDescs = context.resources.getStringArray(R.array.descs)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val view =
            LayoutInflater.from(context).inflate(R.layout.single_onboarding, container, false)

        view.tvTitle.text = arTitles.get(position)
        view.tvDesc.text = arDescs.get(position)

        Picasso
            .get()
            .load(arImages.get(position))
            .noFade()
            .into(  view.bgAnother, object : Callback {
                override fun onSuccess() {


                }

                override fun onError(e: Exception?) {
                }
            })

        container.addView(view)
        return view
    }

    override fun getCount(): Int {
        return arTitles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as View)
    }

}