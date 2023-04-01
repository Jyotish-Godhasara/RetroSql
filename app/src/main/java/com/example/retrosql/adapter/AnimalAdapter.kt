package com.example.retrosql.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.retrosql.R
import com.example.retrosql.database.DatabaseHandler
import com.example.retrosql.model.ResponseModel

open class AnimalAdapter(val context: Context, val databaseHandler: DatabaseHandler) :
    RecyclerView.Adapter<AnimalAdapter.ViewHolder>(), Filterable {

    var items: ArrayList<ResponseModel> = ArrayList()
    var mainList: ArrayList<ResponseModel> = ArrayList()

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_home, parent, false)
        )
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = items?.get(position) ?: return
        holder.tvTtitle.text = "Title : ${data.title}".trim()
        holder.tvAlbumId.text = "Album id : ${data.id}".trim()
        if (databaseHandler.checkIsFavourite(data.id.toString())) {
            holder.ivFavourite.setImageResource(R.drawable.ic_favourite)
        } else {
            holder.ivFavourite.setImageResource(R.drawable.ic_unfavorite)
        }

        Glide.with(context)
            .load(data.thumbnailUrl)
            .into(holder.thumbNail)

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAlbumId = view.findViewById<TextView>(R.id.tvAlbumId)
        val tvTtitle = view.findViewById<TextView>(R.id.tvTtitle)
        val thumbNail = view.findViewById<ImageView>(R.id.thumbNail)
        val ivFavourite = view.findViewById<ImageView>(R.id.ivFavourite)

        init {
            itemView.setOnClickListener {
                onItemClick(items?.get(adapterPosition), adapterPosition)
            }

            ivFavourite.setOnClickListener {
                onFavouriteClick(items?.get(adapterPosition), adapterPosition)
            }
        }
    }

    open fun onFavouriteClick(get: ResponseModel?, adapterPosition: Int) {

    }

    open fun onItemClick(get: ResponseModel?, adapterPosition: Int) {

    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(body: ArrayList<ResponseModel>?) {
        body?.let { items?.addAll(it) }
        body?.let { mainList?.addAll(it) }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    items = mainList
                } else {
                    val resultList = ArrayList<ResponseModel>()
                    for (row in mainList) {
                        if (row.title?.contains(charSearch) == true) {
                            resultList.add(row)
                        }
                    }
                    items = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = items
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                items = results?.values as ArrayList<ResponseModel>
                notifyDataSetChanged()
            }

        }
    }
}


