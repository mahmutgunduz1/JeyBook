package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment

import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter
import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter2

import SliderAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter3
import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter4
import com.mahmutgunduz.jeybook.Model.BooksModel
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.Service.BookAPI
import com.mahmutgunduz.jeybook.databinding.FragmentDiscoverBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DiscoverFragment : Fragment() {
    private var _binding: FragmentDiscoverBinding? = null
    private val binding get() = _binding!!

    private val BASE_URL = "https://www.googleapis.com/books/v1/"

    private lateinit var horizontalAdapter: HorizontalAdapter
    private lateinit var horizontalAdapter2: HorizontalAdapter2
    private lateinit var horizontalAdapter3: HorizontalAdapter3
    private lateinit var horizontalAdapter4: HorizontalAdapter4


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiscoverBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        loadData()
        setupViewPager()

        setupRecyclerViews()

      binding.imageCategories.setOnClickListener {
          imageCategoriess()

        }

    }


    fun imageCategoriess (){



        //searchView gidicen







    }
    private fun setupRecyclerViews() {
        horizontalAdapter = HorizontalAdapter(emptyList())
        binding.rcv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcv.adapter = horizontalAdapter



        horizontalAdapter2 = HorizontalAdapter2(emptyList())
        binding.rcv2.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcv2.adapter = horizontalAdapter2


        horizontalAdapter3 = HorizontalAdapter3(emptyList())
        binding.rcv3.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcv3.adapter = horizontalAdapter3

        horizontalAdapter4 = HorizontalAdapter4(emptyList())
        binding.rcv4.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rcv4.adapter = horizontalAdapter4

    }
    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(BookAPI::class.java)
        val call = service.getData("android")
        call.enqueue(object : Callback<BooksModel> {
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {
                if (response.isSuccessful) {
                    val booksModel = response.body()
                    booksModel?.items?.let {
                        horizontalAdapter.updateData(it)
                    }
                } else {
                    Log.e("DiscoverFragment", "Error: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Log.e("DiscoverFragment", "Failure: ${t.message}")
            }
        })
        val call2 = service.getData("history")
        call2.enqueue(object : Callback<BooksModel> {
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {
                if (response.isSuccessful) {
                    val booksModel = response.body()
                    booksModel?.items?.let {
                        horizontalAdapter2.updateData2(it)
                    }
                }
            }

            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Log.e("DiscoverFragment", "Failure: ${t.message}")
            }
        })

        val call3 = service.getData("Design")
        call3.enqueue(object : Callback<BooksModel> {
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {
                if (response.isSuccessful) {
                    val booksModel = response.body()
                    booksModel?.items?.let {
                        horizontalAdapter3.updateData3(it)
                    }
                }
            }

            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Log.e("DiscoverFragment", "Failure: ${t.message}")
            }
        })


        val call4 = service.getData("Cooking")
        call4.enqueue(object : Callback<BooksModel> {
            override fun onResponse(call: Call<BooksModel>, response: Response<BooksModel>) {
                if (response.isSuccessful) {
                    val booksModel = response.body()
                    booksModel?.items?.let {

                        horizontalAdapter4.updateData4(it)
                    }
                }
            }

            override fun onFailure(call: Call<BooksModel>, t: Throwable) {
                Log.e("DiscoverFragment", "Failure: ${t.message}")
            }
        })
    }

    private fun setupViewPager() {
        val binding = _binding ?: return // Eğer binding null ise, fonksiyondan çık
        val imageResIds = listOf(
            R.drawable.slyat1,
            R.drawable.slyat2
        )

        val adapter = SliderAdapter(requireContext(), imageResIds)
        binding.viewPager.adapter = adapter

        // Slaytların otomatik geçişi için bir zamanlayıcı
        binding.viewPager.postDelayed(object : Runnable {
            var currentPage = 0
            override fun run() {
                if (currentPage == imageResIds.size) {
                    currentPage = 0
                }
                binding.viewPager.setCurrentItem(currentPage++, true)
                binding.viewPager.postDelayed(this, 3000) // 3 saniyede bir slayt geçişi
            }
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    private fun recyclerItemAnimation(recyclerView: RecyclerView) {

        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }


}
