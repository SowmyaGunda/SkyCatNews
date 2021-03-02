package com.example.skycatnews.model.image

import android.widget.ImageView
import com.example.skycatnews.R
import com.squareup.picasso.Picasso

class ImageLoader {
    companion object {
        fun loadImage(url: String, image: ImageView) {
            if (url.isNotEmpty()) {
                Picasso.get().load(url)
                        .placeholder(R.drawable.placeholder).into(image)
            }
        }
    }
}