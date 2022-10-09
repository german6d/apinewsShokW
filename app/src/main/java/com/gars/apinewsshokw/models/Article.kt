package com.gars.apinewsshokw.models

import java.io.Serializable

data class Article (


    var id: Int? =null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val title: String?,
    var url: String,
    var urlToImage: String?
) : Serializable
