package com.gars.apinewsshokw.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.*
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.gars.apinewsshokw.R
import com.gars.apinewsshokw.adapters.NewsProfileAdapter
import com.gars.apinewsshokw.databinding.FragmentArticleWebViewBinding
import com.gars.apinewsshokw.databinding.FragmentNewsProfileBinding
import com.gars.apinewsshokw.db.DBHelper
import com.gars.apinewsshokw.models.ArticleDB
import com.google.android.material.appbar.AppBarLayout


class ArticleWebViewFragment : Fragment() {

    private var _binding: FragmentArticleWebViewBinding? = null
    private var _binding2: FragmentNewsProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var webView: WebView
    private lateinit var appBarLayout: AppBarLayout
    private lateinit var toolbar: Toolbar
    private lateinit var article_title_db: String
    private lateinit var article_url_db: String
    private lateinit var article_url_image_db: String

    lateinit var nsRV: RecyclerView
    lateinit var nsList: ArrayList<ArticleDB>
    lateinit var nsRVAdapter: NewsProfileAdapter

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentArticleWebViewBinding.inflate(inflater, container, false)

        _binding2 = FragmentNewsProfileBinding.inflate(inflater, container, false)

        webView = _binding!!.webViewArticle



        val arguments = arguments
        val urlTitle = arguments!!.getString("article_title")
        val urlNew = arguments.getString("article_url")
        val urlImageNew = arguments.getString("article_url_image")

        if (urlNew != null && urlTitle != null && urlImageNew != null) {
            article_title_db = urlTitle
            article_url_db = urlNew
            article_url_image_db = urlImageNew
        }


        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        urlNew?.let { webView.loadUrl(it) }



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (context as AppCompatActivity).setSupportActionBar(_binding?.toolbar)
        setHasOptionsMenu(true)
        _binding?.toolbar?.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        _binding?.toolbar?.setNavigationOnClickListener{

            requireActivity().onBackPressed()
        }

        _binding?.toolbar?.title = ""

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)

        validarNoticiaGuardada(menu)
    }

    private fun validarNoticiaGuardada(menu: Menu) {

        val db = _binding?.webViewArticle?.let { DBHelper(it.context, null) }

        val cursor: Cursor? = db?.getArticlesByURL(article_url_db)

        if (cursor != null) {

            val iconSave = menu.findItem(R.id.action_save)
            val iconDelete = menu.findItem(R.id.action_delete)


            if (cursor.count >= 1) {

                iconSave.isVisible = false


            } else {
                iconDelete.isVisible = false
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId


        if (id == R.id.action_save) {


            guardarNoticia(requireContext())
            item.isVisible = false


        }

        if (id == R.id.action_delete) {


            eliminarNoticia(article_url_db)
            item.isVisible = false

        }

        return super.onOptionsItemSelected(item)
    }

    private fun eliminarNoticia(article_url_db: String) {

        val db = _binding?.webViewArticle?.let { DBHelper(it.context, null) }

        db!!.deleteArticle(article_url_db)

        Toast.makeText(binding.webViewArticle.context, "Noticia eliminada de favoritas.",
            Toast.LENGTH_LONG).show()

    }

    private fun guardarNoticia(context: Context) {

        val db = DBHelper(context, null)

        db.addArticle(article_title_db, article_url_db, article_url_image_db)

        db.close()

        Toast.makeText(context, "Noticia favorita guardada.", Toast.LENGTH_LONG).show()

    }



}