package com.example.skycatnews.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.skycatnews.R
import com.example.skycatnews.model.data.NewsStory
import com.example.skycatnews.model.data.StoryContentType
import com.example.skycatnews.model.data.StoryContentTypeTypeImage
import com.example.skycatnews.model.data.StoryContentTypeTypeParagraph
import com.example.skycatnews.model.image.ImageLoader

class StoryListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    enum class ContentType(val type: String) {
        PARAGRAPH("paragraph"),
        IMAGE("image")
    }

    private var storyList: List<StoryContentType> = emptyList()


    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ContentType.PARAGRAPH.ordinal) {
            StoryContentHolder(parent.inflate(R.layout.story_item_paragraph))
        } else {
            StoryImageHolder(parent.inflate(R.layout.story_item_image))
        }

    }

    fun setAdapterList(list: List<StoryContentType>) {
        this.storyList = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = storyList.size
    override fun getItemViewType(position: Int): Int {
        return if (storyList[position].type == ContentType.PARAGRAPH.type) {
            ContentType.PARAGRAPH.ordinal
        } else {
            ContentType.IMAGE.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val storyContent = storyList[position]
        when (holder) {
            is StoryContentHolder -> holder.story_content.text = (storyContent as StoryContentTypeTypeParagraph).text
            is StoryImageHolder -> {
                ImageLoader.loadImage((storyContent as StoryContentTypeTypeImage).url, holder.story_Image)
            }
        }

    }

    class StoryContentHolder(v: View) : RecyclerView.ViewHolder(v) {
        val story_content: TextView = v.findViewById(R.id.story_content)
    }

    class StoryImageHolder(v: View) : RecyclerView.ViewHolder(v) {
        val story_Image: ImageView = v.findViewById(R.id.story_image)
    }
}