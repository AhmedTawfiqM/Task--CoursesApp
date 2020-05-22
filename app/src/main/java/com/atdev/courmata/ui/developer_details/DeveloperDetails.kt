package com.atdev.courmata.ui.developer_details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.atdev.courmata.R
import com.atdev.courmata.pojo.utilities.Constants.DEVELOPER
import com.atdev.courmata.pojo.utilities.Constants.Mansour
import com.atdev.courmata.pojo.utilities.Constants.Mansour_FB
import com.atdev.courmata.pojo.utilities.Constants.Mansour_LINKEDIN
import com.atdev.courmata.pojo.utilities.Constants.TAwfiq
import com.atdev.courmata.pojo.utilities.Constants.TAwfiq_FB
import com.atdev.courmata.pojo.utilities.Constants.TAwfiq_LINKEDIN
import com.atdev.courmata.pojo.utilities.Utils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_developer_details.*


class DeveloperDetails : AppCompatActivity() {

    lateinit var developer: String

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer_details)
        //
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back)
        //
        developer = intent.getStringExtra(DEVELOPER)!!
        checkDevloper()
        soacialMediaListeners()
    }

    private fun soacialMediaListeners() {

        ivFB.setOnClickListener {
            when (developer) {
                TAwfiq -> startActivity(Utils.newFacebookIntent(packageManager, TAwfiq_FB))
                Mansour -> startActivity(Utils.newFacebookIntent(packageManager, Mansour_FB))
                else -> Log.d("", "")
            }
        }
        //
        ivLinkedIN.setOnClickListener {
            when (developer) {
                TAwfiq -> openURL(TAwfiq_LINKEDIN)
                Mansour -> openURL(Mansour_LINKEDIN)
                else -> Log.d("", "")
            }
        }
        //
        ivGitHub.setOnClickListener {
            when (developer) {
                TAwfiq -> openURL("https://github.com/AhmedTawfiqM")
                Mansour -> openURL("https://github.com/proahmedmansour")
                else -> Log.d("", "")
            }
        }
        //
        ivTwitter.setOnClickListener {
            when (developer) {
                TAwfiq -> openURL("https://twitter.com/Ahmedta93348177")
                Mansour -> openURL("https://twitter.com/ProAhmedMansour?fbclid=IwAR2x54lA9izmsJuFKLGZxYqtQ7MjXanjeGlbcVXIYHNbgJ52ORefGgknInQ")
                else -> Log.d("", "")
            }
        }
        //
        ivInstigram.setOnClickListener {
            when (developer) {
                TAwfiq -> openURL("https://www.instagram.com/ahmed_a.tawfiq/")
                Mansour -> openURL("https://www.instagram.com/ProAhmedMansour/?fbclid=IwAR1KvaHWzq_lFOYzfnI0krugns1O6695twhcVNlJSK94FZmWTDbciIyrgvI")
                else -> Log.d("", "")
            }
        }
    }

    private fun checkDevloper() {

        when (developer) {
            TAwfiq -> loadTawfiqData()
            Mansour -> loadMansourData()
            else -> Log.d("", "")
        }

    }

    fun loadData(image: Int, name: String, email: String, phone: String) {
        //
        Picasso.get().load(image).into(ivDeveloper)
        tvNameDeveloper.text = name
        tvEmail.text = email
        tvPhone.text = phone
        //
    }

    private fun loadMansourData() {
        loadData(
            R.drawable.mansour, "Ahmed Mansour", "ahmedmanssur@outlook.com", "+201276222569"
        )

    }

    private fun loadTawfiqData() {

        loadData(
            R.drawable.tifa3, "Ahmed Tawfiq", "ahmedadelmm26@gmail.com", "+201159545785"
        )

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {

            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)

    }

    private fun openURL(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}
