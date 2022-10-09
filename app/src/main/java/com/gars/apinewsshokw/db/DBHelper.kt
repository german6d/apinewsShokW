package com.gars.apinewsshokw.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.gars.apinewsshokw.models.ArticleDB

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                TITLE_COl + " TEXT," +
                URL_COL + " TEXT," +
                URL_IMAGE_COL + " TEXT" + ")")


        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addArticle(title: String, url : String, urlImage : String ){

        val values = ContentValues()

        values.put(TITLE_COl, title)
        values.put(URL_COL, url)
        values.put(URL_IMAGE_COL, urlImage)


        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)


        db.close()
    }


    fun getArticles(): Cursor? {


        val db = this.readableDatabase


        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }

    fun getArticlesByURL(url: String): Cursor? {


        val db = this.readableDatabase


        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $URL_COL = '$url'", null)


    }

    fun deleteArticle(url: String) {

        val db = this.writableDatabase

        db.execSQL("DELETE FROM $TABLE_NAME WHERE $URL_COL='$url'");
        db.close()
    }

    fun readCourses(): ArrayList<ArticleDB> {

        val db = this.readableDatabase

        val cursorCourses = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val courseModalArrayList: ArrayList<ArticleDB> = ArrayList()

        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    ArticleDB(
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3)
                    )
                )
            } while (cursorCourses.moveToNext())
        }

        cursorCourses.close()
        return courseModalArrayList
    }

    companion object{

            private val DATABASE_NAME = "ARTICLES_DB"

            private val DATABASE_VERSION = 1

            val TABLE_NAME = "ARTICLES"

            val ID_COL = "id"

            val TITLE_COl = "title_new"

            val URL_COL = "url_new"

            val URL_IMAGE_COL = "url_image"
        }
}

