package com.gars.apinewsshokw.fragments

import android.R.attr.data
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gars.apinewsshokw.adapters.NewsProfileAdapter
import com.gars.apinewsshokw.databinding.FragmentNewsProfileBinding
import com.gars.apinewsshokw.db.DBHelper
import com.gars.apinewsshokw.models.ArticleDB
import kotlin.concurrent.fixedRateTimer


class NewsProfileFragment : Fragment() {

    private var _binding: FragmentNewsProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsProfileAdapter
    private val newsProfileImages = mutableListOf<ArticleDB>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNewsProfileBinding.inflate(inflater, container, false)

        consultarNoticias()

        return binding.root

    }


    fun consultarNoticias() {

        fixedRateTimer("timer",false,0,1000){
            activity?.runOnUiThread {

                updateData()
            }
        }

        val db = DBHelper(requireContext(), null)

        val cursor: Cursor? = db.getArticles()
        if (cursor != null) {
            while (cursor.moveToNext()) {

                newsProfileImages.clear()

                val newsProfileArrayList = db.readCourses()

                if (newsProfileArrayList.isNotEmpty()) {
                    newsProfileImages.addAll(newsProfileArrayList.reversed())
                }

                adapter = NewsProfileAdapter(newsProfileImages)
                binding.rvNewsProfile.layoutManager = LinearLayoutManager(context)
                binding.rvNewsProfile.adapter = adapter

                adapter.notifyDataSetChanged()

            }
        }
    }

    fun updateData() {

        val db = DBHelper(requireContext(), null)
        val oldList = newsProfileImages.size
        val newsProfileArrayList2 = db.readCourses()
        val newsProfileArrayList3 = newsProfileArrayList2.reversed()
        val newList = newsProfileArrayList2.size

        if(newList != oldList) {

            if (newsProfileArrayList2.isNotEmpty()) {

                adapter = NewsProfileAdapter(newsProfileArrayList3 as MutableList<ArticleDB>)
                binding.rvNewsProfile.layoutManager = LinearLayoutManager(context)
                binding.rvNewsProfile.adapter = adapter
                adapter.notifyDataSetChanged()
                newsProfileImages.clear()
                newsProfileImages.addAll(newsProfileArrayList2)

            } else {

                /*binding.rvNewsProfile.adapter = null
                adapter.notifyDataSetChanged()*/
                adapter = NewsProfileAdapter(newsProfileImages)
                binding.rvNewsProfile.layoutManager = LinearLayoutManager(context)
                binding.rvNewsProfile.adapter = null

                adapter.notifyDataSetChanged()
            }
        }
    }







}