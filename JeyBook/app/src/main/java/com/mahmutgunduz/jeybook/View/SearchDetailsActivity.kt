package com.mahmutgunduz.jeybook.View

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Adapter.SearchAdapter
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.Model.BooksModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.Service.BookAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class SearchDetailsActivity : AppCompatActivity() {

    private val BASE_URL = "https://www.googleapis.com/books/v1/"
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterr: SearchAdapter
    private lateinit var searchView : SearchView

    private lateinit var searchResults: ArrayList<BookItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_search_details)

        recyclerView = findViewById(R.id.recyclerView)


        searchResults = ArrayList()
        adapterr = SearchAdapter(searchResults)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterr
        // Intent ile gelen arama sorgusunu al
        val query = intent.getStringExtra("QUERY") ?: ""
        // Arama işlemini başlat
        searchBooks(query)



    }

    private fun searchBooks(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(BookAPI::class.java)
        val call = service.getData(query,40)

        call.enqueue(object : Callback<BooksModel> {
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {
                if (response.isSuccessful) {
                    val books = response.body()?.items ?: emptyList()
                    adapterr.updateData(books)
                } else {
                    Log.e("SearchDetailsActivity", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Log.e("SearchDetailsActivity", "Failure: ${t.message}")
            }
        })


    }
}
