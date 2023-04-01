package com.example.retrosql.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrosql.R
import com.example.retrosql.activity.DetailScreen
import com.example.retrosql.adapter.AnimalAdapter
import com.example.retrosql.api.ApiInterface
import com.example.retrosql.api.RetrofitClient
import com.example.retrosql.database.DatabaseHandler
import com.example.retrosql.model.ResponseModel
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    private lateinit var templist: ArrayList<ResponseModel>
    val databaseHandler by lazy { DatabaseHandler(requireContext()) }

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private val isAllEmailLoaded: Boolean = false
    private lateinit var response: Call<ArrayList<ResponseModel>>
    private lateinit var apiInterface: ApiInterface
    var retrofit = RetrofitClient.getInstance()

    lateinit var rcvList: RecyclerView
    lateinit var adapter: AnimalAdapter
    lateinit var tieSearchWord: TextInputEditText

    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        apiInterface = retrofit.create(ApiInterface::class.java)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvList = view.findViewById(R.id.rcvList)
        tieSearchWord = view.findViewById(R.id.tieSearchWord)

        tieSearchWord?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(text: Editable?) {
                adapter.filter.filter(text)
                /*if (!p0.isNullOrEmpty()) {
                    filter(p0.toString());
                } else {
                    adapter.updateData(adapter.mainList)
                }*/
            }
        })

        adapter = object : AnimalAdapter(requireContext(), databaseHandler) {
            override fun onFavouriteClick(get: ResponseModel?, adapterPosition: Int) {

                super.onFavouriteClick(get, adapterPosition)
                if (!databaseHandler.checkIsFavourite(get?.id.toString())) {
                    databaseHandler.addToFavourite(get)
                } else {
                    databaseHandler.deleteFromFavourite(get)
                }
                notifyItemChanged(adapterPosition)
            }

            override fun onItemClick(get: ResponseModel?, adapterPosition: Int) {
                super.onItemClick(get, adapterPosition)

                val intent = Intent(requireContext(), DetailScreen::class.java)
                intent.putExtra("data", Gson().toJson(get))
                startActivity(intent)
            }
        }

        rcvList.adapter = adapter

        fetchEmails()

        rcvList.addOnScrollListener(object :
            PaginationScrollListener(rcvList.layoutManager as LinearLayoutManager) {
            override fun loadMoreItems() {
                isLoading = true;
                page += 1
                fetchEmails()
            }

//            override fun isLastPage() = isLastPage

            override fun isLoading() = isLoading
        })
    }

    private fun fetchEmails() {
        response = apiInterface.getAllUsers(page)
        response.enqueue(object : Callback<ArrayList<ResponseModel>?> {
            override fun onResponse(
                call: Call<ArrayList<ResponseModel>?>, response: Response<ArrayList<ResponseModel>?>
            ) {
                isLoading = false
                adapter.updateData(response.body())

                Log.d("TAG", "onCreateView: " + Gson().toJson(response.body()))
            }

            override fun onFailure(call: Call<ArrayList<ResponseModel>?>, t: Throwable) {
                Log.d("TAG", "onCreateView: " + t.message)
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }

    /*fun filter(text: String) {
        val temp: ArrayList<ResponseModel> = ArrayList()
        for (d in adapter?.mainList ?: arrayListOf()) {
            if (d.title?.contains(text) == true) {
                temp.add(d)
                Log.d("TAG", "filter: " + temp.size)
            }
        }
        adapter.items?.clear()
        adapter.updateData(temp)
    }*/

}