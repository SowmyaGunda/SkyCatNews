package com.example.skycatnews.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.skycatnews.R

class CatNewsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cat_news_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, CatNewsFragment.newInstance())
                    .commitNow()
        }
    }
}