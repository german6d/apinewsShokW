package com.gars.apinewsshokw.adapters

import android.database.Cursor
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gars.apinewsshokw.R
import com.gars.apinewsshokw.databinding.ItemNewsBinding
import com.gars.apinewsshokw.databinding.ItemNewsFavoritesBinding
import com.gars.apinewsshokw.db.DBHelper
import com.gars.apinewsshokw.fragments.ArticleWebViewFragment
import com.gars.apinewsshokw.fragments.HomeFragment
import com.squareup.picasso.Picasso


class NewsProfileViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemNewsFavoritesBinding.bind(view)

    fun bind(image: String, title: String, urlNew: String) {

        val db = DBHelper(binding.ivNewImage.context, null)

        Picasso.get().load(image).into(binding.ivNewImage)

        binding.tvNews.text = title


        binding.root.setOnClickListener {

            val activity  = it.context as AppCompatActivity
            val articleViewFragment = ArticleWebViewFragment()
            val homeFragment = HomeFragment()

            val arguments = Bundle()
            arguments.putString("article_title", title )
            arguments.putString("article_url", urlNew )
            arguments.putString("article_url_image", image )
            articleViewFragment.arguments = arguments

            activity.supportFragmentManager.beginTransaction()
                .remove(homeFragment)
                .commitAllowingStateLoss()

            activity.supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, articleViewFragment).addToBackStack(null)
                .commit()

        }

    }



}