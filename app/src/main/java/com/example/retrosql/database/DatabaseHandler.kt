package com.example.retrosql.database

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.retrosql.model.ResponseModel

class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "FAVOURITEDATABASE"
        private val TABLE_CONTACTS = "FavouriteData"
        private val KEY_ID = "id"
        private val title = "title"
        private val image = "image"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + title + " TEXT,"
                + image + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }

    //method to insert data
    fun addToFavourite(emp: ResponseModel?): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp?.id)
        contentValues.put(title, emp?.title)
        contentValues.put(image, emp?.thumbnailUrl)
        // Inserting Row
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }

    fun checkIsFavourite(id: String): Boolean {
        val db = this.readableDatabase

        val columns = arrayOf<String>(KEY_ID)
        val selection: String = KEY_ID + " =?"
        val selectionArgs = arrayOf(id)
        val limit = "1"
        val cursor: Cursor =
            db.query(TABLE_CONTACTS, columns, selection, selectionArgs, null, null, null, limit)
        val exists: Boolean = cursor.count > 0
        cursor.close()
        return exists
    }

    fun deleteFromFavourite(emp: ResponseModel?): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp?.id) // EmpModelClass UserId
        // Deleting Row
        val success = db.delete(TABLE_CONTACTS, "id=" + emp?.id, null)
        //2nd argument is String containing nullColumnHack
        db.close() // Closing database connection
        return success
    }


    //method to read data
    @SuppressLint("Range")
    fun viewEmployee(): ArrayList<ResponseModel> {
        val empList: ArrayList<ResponseModel> = ArrayList<ResponseModel>()
        val selectQuery = "SELECT  * FROM $TABLE_CONTACTS"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var id: Int
        var title: String
        var thumbnailUrl: String
        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                title = cursor.getString(cursor.getColumnIndex("title"))
                thumbnailUrl = cursor.getString(cursor.getColumnIndex("image"))
                val emp = ResponseModel(id = id, title = title, thumbnailUrl = thumbnailUrl)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }
}