package com.gars.apinewsshokw.models

import java.io.Serializable

class ArticleDB : Serializable {
    var title: String? = null
    var url: String? = null
    var urlToImage: String? = null

    constructor(title: String?, url: String?, urlToImage: String?) {
        this.title = title
        this.url = url
        this.urlToImage = urlToImage
    }

    constructor() {}
}