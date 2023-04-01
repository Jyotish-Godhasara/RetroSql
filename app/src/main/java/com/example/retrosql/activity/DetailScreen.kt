package com.example.retrosql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.retrosql.R
import com.example.retrosql.database.DatabaseHandler
import com.example.retrosql.model.ResponseModel
import com.google.gson.Gson

class DetailScreen : AppCompatActivity() {
    val databaseHandler by lazy { DatabaseHandler(this) }

    lateinit var thumbNail: ImageView
    lateinit var tvAlbumId: TextView
    lateinit var tvTtitle: TextView
    lateinit var ivFavourite: ImageView
    lateinit var ivBack: ImageView

    var data: ResponseModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        data = Gson().fromJson(intent.getStringExtra("data"), ResponseModel::class.java)

        thumbNail = findViewById(R.id.thumbNail)
        tvAlbumId = findViewById(R.id.tvAlbumId)
        tvTtitle = findViewById(R.id.tvTtitle)
        ivFavourite = findViewById(R.id.ivFavourite)
        ivBack = findViewById(R.id.ivBack)


        tvAlbumId.text = "Albumid : ${data?.id}".trim()
        tvTtitle.text = "Title : ${data?.title}".trim()

        Glide.with(this)
            .load(data?.url)
            .into(thumbNail)

        chekStatus()

        ivBack.setOnClickListener {
            finish()
        }
        ivFavourite.setOnClickListener {
            if (!databaseHandler.checkIsFavourite(data?.id.toString())) {
                databaseHandler.addToFavourite(data)
            } else {
                databaseHandler.deleteFromFavourite(data)
                finish()
            }
            chekStatus()
        }

    }

    fun chekStatus() {
        if (databaseHandler.checkIsFavourite(data?.id.toString())) {
            ivFavourite.setImageResource(R.drawable.ic_favourite)
        } else {
            ivFavourite.setImageResource(R.drawable.ic_unfavorite)
        }
    }
}