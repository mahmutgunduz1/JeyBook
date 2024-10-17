package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.mahmutgunduz.jeybook.Adapter.DesgnFragmentAdapter
import com.mahmutgunduz.jeybook.Model.BooksModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.Service.BookAPI
import com.mahmutgunduz.jeybook.databinding.FragmentDesignBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class DesignFragment : Fragment() {

    private var _binding: FragmentDesignBinding? = null
    private val binding get() = _binding!!
    private lateinit var desgnadapter: DesgnFragmentAdapter
    private val BASE_URL = "https://www.googleapis.com/books/v1/"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDesignBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bundle'dan veri al
        val categoryName = arguments?.getString("Category_Name")

        val textView = view.findViewById<TextView>(R.id.txtDesign)
        textView.text = categoryName // Gelen `text` bilgisini göster
        binding.rcvNfo.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
        desgnadapter = DesgnFragmentAdapter(emptyList())
        binding.rcvNfo.adapter = desgnadapter
        fetchData(categoryName.toString())

    }

    private fun  fetchData(category: String){


        val retrofit =Retrofit.Builder().
       baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create())
            .build()


        val service = retrofit.create(BookAPI::class.java)

        val call = service.getData(category)
        call.enqueue(object :Callback<BooksModel>{
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {

                if (response.isSuccessful){
                    val data = response.body()?.items ?: emptyList()
                    desgnadapter.updateDataDesgn(data)


                }
            }

            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Toast.makeText(requireContext(), "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }


        })


    }

    fun searchDetailsGetData(){




    }




}