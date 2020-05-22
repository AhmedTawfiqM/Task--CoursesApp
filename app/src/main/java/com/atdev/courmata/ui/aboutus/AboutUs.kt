package com.atdev.courmata.ui.aboutus

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.atdev.courmata.R
import com.atdev.courmata.pojo.utilities.CircleTransform
import com.atdev.courmata.pojo.utilities.Constants.DEVELOPER
import com.atdev.courmata.pojo.utilities.Constants.Mansour
import com.atdev.courmata.pojo.utilities.Constants.TAwfiq
import com.atdev.courmata.ui.developer_details.DeveloperDetails
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_about_us.*


class AboutUs : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        //
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        //
        loadOurPhotos()
        cardDevelopersListener()
    }

    private fun cardDevelopersListener() {

        cardMansour.setOnClickListener {
            startActivity(
                Intent(this, DeveloperDetails::class.java)
                    .putExtra(DEVELOPER, Mansour)
            )
        }
        //
        cardTawfiq.setOnClickListener {
            startActivity(
                Intent(this, DeveloperDetails::class.java)
                    .putExtra(DEVELOPER, TAwfiq)
            )
        }
    }

    private fun loadOurPhotos() {

        Picasso.get().load(R.drawable.tawfiq).transform(CircleTransform()).into(ivTawfiq)
        Picasso.get().load(R.drawable.mansour).transform(CircleTransform()).into(ivMansour)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }
}
