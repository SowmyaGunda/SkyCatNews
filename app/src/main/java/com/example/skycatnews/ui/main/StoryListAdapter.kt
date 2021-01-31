package com.example.skycatnews.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.util.StoryContent
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class StoryListAdapter(private val storyList: ArrayList<StoryContent>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class StoryContentType(val type: String) {
        PARAGRAPH("paragraph"),
        IMAGE("image")
    }


    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == StoryContentType.PARAGRAPH.ordinal) {
            StoryContentHolder(parent.inflate(R.layout.story_item_paragraph))
        } else {
            StoryImageHolder(parent.inflate(R.layout.story_item_image))
        }

    }

    override fun getItemCount() = storyList.size
    override fun getItemViewType(position: Int): Int {
        return if (storyList[position].type == StoryContentType.PARAGRAPH.type) {
            StoryContentType.PARAGRAPH.ordinal
        } else {
            StoryContentType.IMAGE.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyContent = storyList[position]
        when (holder) {
            is StoryContentHolder -> (holder as StoryContentHolder).story_content.text = storyContent.text
            is StoryImageHolder -> Picasso.get().load(storyContent.url).placeholder(R.drawable.image_placeholder).into((holder as StoryImageHolder).story_Image)
        }

    }

    class StoryContentHolder(v: View) : RecyclerView.ViewHolder(v) {
        val story_content: TextView = v.findViewById(R.id.story_content)
    }

    class StoryImageHolder(v: View) : RecyclerView.ViewHolder(v) {
        val story_Image: ImageView = v.findViewById(R.id.story_image)
    }
}