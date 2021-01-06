package com.example.submissiongit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setData()
    }

    private fun setData() {
        val gitItems = intent.getParcelableExtra(EXTRA_DATA) as GitItems?



        detail_name.text = gitItems?.name
        detail_username.text = gitItems?.username
        detail_company.text= "Company : {gitItems.company}"
        detail_location.text = "Location : {gitItems.location"
        detail_repository.text = "Repository : {gitItems.repository}"
        Glide.with(this)
            .load(gitItems?.avatar)
            .into(detail_avatar)

    }
}