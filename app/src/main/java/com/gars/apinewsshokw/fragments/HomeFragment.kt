package com.gars.apinewsshokw.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.gars.apinewsshokw.adapters.NewsAdapter
import com.gars.apinewsshokw.api.APIService
import com.gars.apinewsshokw.databinding.FragmentHomeBinding
import com.gars.apinewsshokw.models.Article
import com.gars.apinewsshokw.models.NewsResponse
import com.gars.apinewsshokw.utils.Constants.Companion.API_KEY
import com.gars.apinewsshokw.utils.Constants.Companion.BASE_URL
import com.gars.apinewsshokw.utils.Constants.Companion.END_POINT_NEWS_API
import com.gars.apinewsshokw.utils.Constants.Companion.NEWS_COUNTRY
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NewsAdapter
    private val newsImages = mutableListOf<Article>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerView()
        getNews()

        return binding.root

    }

    private fun initRecyclerView() {

        adapter = NewsAdapter(newsImages)
        binding.rvNews.layoutManager = LinearLayoutManager(context)
        binding.rvNews.adapter = adapter
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private fun getNews(){



        CoroutineScope(Dispatchers.IO ).launch {

            val call = getRetrofit().create(APIService::class.java).getNews(
                "$END_POINT_NEWS_API?country=$NEWS_COUNTRY&apiKey=$API_KEY")

            val news = call.body()

            activity?.runOnUiThread {

                if(call.isSuccessful && call.code() == 200){

                    val images = news?.articles

                    newsImages.clear()

                    if (!images.isNullOrEmpty()) {
                        newsImages.addAll(images)
                    }

                    adapter.notifyDataSetChanged()

                } else{
                    showError(call)
                }

            }


        }
    }

    private fun showError(call: Response<NewsResponse>) {
        Toast.makeText(context, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
        println(call)
    }

}

