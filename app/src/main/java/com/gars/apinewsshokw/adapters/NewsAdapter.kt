package com.gars.apinewsshokw.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gars.apinewsshokw.models.Article


class NewsAdapter (private val images: MutableList<Article>) :  RecyclerView.Adapter<NewsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return NewsViewHolder((layoutInflater.inflate(com.gars.apinewsshokw.R.layout.item_news, parent, false)))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val urlImage: String? = images[position].urlToImage
        val titleNews: String? = images[position].title
        val urlNew: String = images[position].url
        println("item $urlImage")
        holder.bind(urlImage.toString(), titleNews.toString(), urlNew)

    }
}