package com.gars.apinewsshokw.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gars.apinewsshokw.models.ArticleDB


class NewsProfileAdapter(private val images: MutableList<ArticleDB>) :  RecyclerView.Adapter<NewsProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsProfileViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return NewsProfileViewHolder((layoutInflater.inflate(com.gars.apinewsshokw.R.layout.item_news_favorites, parent, false)))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: NewsProfileViewHolder, position: Int) {


        val urlImage: String? = images[position].urlToImage
        val titleNews: String? = images[position].title
        val urlNew: String? = images[position].url
        println("item $urlImage")
        if (urlNew != null) {
            holder.bind(urlImage.toString(), titleNews.toString(), urlNew)
        }
    }


}