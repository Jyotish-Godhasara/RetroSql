package com.example.retrosql.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrosql.R
import com.example.retrosql.adapter.AnimalAdapter
import com.example.retrosql.database.DatabaseHandler
import com.example.retrosql.model.ResponseModel


class FavouriteFragment : Fragment() {
    lateinit var rcvList: RecyclerView
    lateinit var adapter: AnimalAdapter

    val databaseHandler by lazy { DatabaseHandler(requireContext()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvList = view.findViewById(R.id.rcvList)

        adapter = object : AnimalAdapter(requireContext(), databaseHandler) {
            @SuppressLint("NotifyDataSetChanged")
            override fun onFavouriteClick(get: ResponseModel?, adapterPosition: Int) {

                super.onFavouriteClick(get, adapterPosition)
                if (!databaseHandler.checkIsFavourite(get?.id.toString())) {
                    databaseHandler.addToFavourite(get)
                } else {
                    databaseHandler.deleteFromFavourite(get)
                }
                fetchEmails()
            }

            override fun onItemClick(get: ResponseModel?, adapterPosition: Int) {
                super.onItemClick(get, adapterPosition)
            }
        }

        rcvList.adapter = adapter
    }

    private fun fetchEmails() {
        adapter.items?.clear()

        val list = databaseHandler.viewEmployee()
        adapter.updateData(list)
    }

    override fun onResume() {
        super.onResume()
        fetchEmails()
    }
}