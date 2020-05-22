package com.atdev.courmata.pojo.utilities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import com.atdev.courmata.R
import com.atdev.courmata.pojo.model.Course
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


object Utils {
     fun showToast(context: Context,message: CharSequence) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    fun getCallBack(): DiffUtil.ItemCallback<Course> {

        return object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(oldCourse: Course, newCourse: Course): Boolean {
                return oldCourse.id.equals(newCourse.id)
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldCourse: Course, newCourse: Course): Boolean {
                return oldCourse.equals(newCourse)
            }
        }
    }

    fun loadCourseImage(url: String, ivCourse: ImageView) {

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

    fun isInternetAvailable(context: Context): Boolean {
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }

                }
            }
        }

        return result
    }
    fun newFacebookIntent(pm: PackageManager, url: String): Intent? {
        var uri: Uri = Uri.parse(url)
        try {
            val applicationInfo = pm.getApplicationInfo("com.facebook.katana", 0)
            if (applicationInfo.enabled) {
                // http://stackoverflow.com/a/24547437/1048340
                uri = Uri.parse("fb://facewebmodal/f?href=$url")
            }
        } catch (ignored: PackageManager.NameNotFoundException) {
        }
        return Intent(Intent.ACTION_VIEW, uri)
    }//
    fun openLinkedIN(context: Context, url: String){
        //
        var intent = Intent(Intent.ACTION_VIEW, Uri.parse("linkedin://you"))
        val packageManager: PackageManager = context.getPackageManager()
        val list =
            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        if (list.isEmpty()) {
            intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(url)
            )
        }
        context.startActivity(intent)
    }

}