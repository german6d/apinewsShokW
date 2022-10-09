package com.gars.apinewsshokw.adapters

import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gars.apinewsshokw.databinding.ItemNewsBinding
import com.gars.apinewsshokw.db.DBHelper
import com.gars.apinewsshokw.fragments.ArticleWebViewFragment
import com.gars.apinewsshokw.fragments.HomeFragment
import com.squareup.picasso.Picasso


class NewsViewHolder (view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemNewsBinding.bind(view)

    fun bind(image: String, title: String, urlNew: String) {

        val db = DBHelper(binding.ivNewImage.context, null)

        Picasso.get().load(image).into(binding.ivNewImage)

        binding.tvNews.text = title

        val cursor: Cursor? = db.getArticlesByURL(urlNew)

        if (cursor != null) {



                if (cursor.count >= 1) {

                    while (cursor.moveToNext()) {

                        val label = cursor.getString(2)
                        //println("holder count 1 " + cursor.count)


                        if (label == urlNew) {
                            println("holder count 1 $label")
                            binding.ivNewSaveRemove.visibility = View.VISIBLE
                            binding.ivNewSave.visibility = View.GONE
                        }

                    }
                }

                if (cursor.count == 0) {
                    println("holder count 0 " + cursor.count)
                    binding.ivNewSave.visibility = View.VISIBLE

                }

                db.close()


        }



        binding.LinearGoNew.setOnClickListener {

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

        binding.ivNewSave.setOnClickListener {

            binding.ivNewSave.visibility = View.GONE

            db.addArticle(title, urlNew, image)

            Toast.makeText(binding.ivNewImage.context, "Noticia favorita guardada.",
                Toast.LENGTH_LONG).show()


            binding.ivNewSaveRemove.visibility = View.VISIBLE



        }

        binding.ivNewSaveRemove.setOnClickListener {

            binding.ivNewSaveRemove.visibility = View.GONE

            db.deleteArticle(urlNew)

            Toast.makeText(binding.ivNewImage.context, "Noticia eliminada de favoritas.",
                Toast.LENGTH_LONG).show()


            binding.ivNewSave.visibility = View.VISIBLE

        }

    }



}