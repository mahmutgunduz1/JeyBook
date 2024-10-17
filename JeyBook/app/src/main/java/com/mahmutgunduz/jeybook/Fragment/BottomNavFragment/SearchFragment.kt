package com.mahmutgunduz.jeybook.Fragment.BottomNavFragment


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils;
import com.mahmutgunduz.jeybook.R

import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Adapter.MultiViewAdapter
import com.mahmutgunduz.jeybook.Model.CardInfoModel2
import com.mahmutgunduz.jeybook.Model.CardİnfoModel
import com.mahmutgunduz.jeybook.View.SearchDetailsActivity
import com.mahmutgunduz.jeybook.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var m :MultiViewAdapter
    private var list: ArrayList<CardİnfoModel> = ArrayList() // Başlatma
    private var list2: ArrayList<CardInfoModel2> = ArrayList() // Başlatma

    private val BASE_URL = "https://www.googleapis.com/books/v1/"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchView = binding.searchView
// SearchView içindeki TextView'in ID'sini alıyoruz
        val searchSrcTextId = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<TextView>(searchSrcTextId)
        textView.setTextColor(Color.BLACK)





       recyclerTry()
        setupSearchView()
    }
    fun recyclerTry(){

        list.add(CardİnfoModel("Kurgu"))
        list.add(CardİnfoModel("Kurgusal Olmayan"))
        list.add(CardİnfoModel("Bilim"))
        list.add(CardİnfoModel("Teknoloji"))
        list.add(CardİnfoModel("Tarih"))
        list.add(CardİnfoModel("Biyografi"))
        list.add(CardİnfoModel("Edebiyat"))
        list.add(CardİnfoModel("Felsefe"))
        list.add(CardİnfoModel("Sağlık"))
        list.add(CardİnfoModel("İş ve Ekonomi"))
        list.add(CardİnfoModel("Kişisel Gelişim"))
        list.add(CardİnfoModel("Eğitim"))
        list.add(CardİnfoModel("Din"))
        list.add(CardİnfoModel("Seyahat"))
        list.add(CardİnfoModel("Politika"))
        list.add(CardİnfoModel("Sanat"))

        m = MultiViewAdapter(list, requireContext())

        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rcv1.layoutManager = layoutManager
        binding.rcv1.adapter = m
        recyclerItemAnimation(binding.rcv1)




    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Memory leak'i önlemek için binding'i null yapıyoruz
        _binding = null
    }

    fun setupSearchView (){


        binding.searchView.clearFocus()



        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                p0?.let {

                    binding.searchView.clearFocus()

                    val intent = Intent(context, SearchDetailsActivity::class.java)
                    intent.putExtra("QUERY", it)
                    startActivity(intent)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }


        })
    }



    private fun recyclerItemAnimation(recyclerView: RecyclerView) {

        val controller =
            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        recyclerView.layoutAnimation = controller
        recyclerView.scheduleLayoutAnimation()
    }


}