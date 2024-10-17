package com.mahmutgunduz.jeybook.Fragment.SaveTabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter
import com.mahmutgunduz.jeybook.Adapter.DiscoverRcv.HorizontalAdapter4
import com.mahmutgunduz.jeybook.Adapter.SaveFragmentBookShowAdapter.CurrentlyShowAdapter
import com.mahmutgunduz.jeybook.Model.BookLoadModel
import com.mahmutgunduz.jeybook.Model.CurrentlyShowModel
import com.mahmutgunduz.jeybook.Model.PostModel
import com.mahmutgunduz.jeybook.R


class CurrentlyReadingFragment : Fragment() {
    private lateinit var adapter: CurrentlyShowAdapter
    private lateinit var rcv: RecyclerView
    private lateinit var db: FirebaseFirestore

    lateinit var postArrayList: ArrayList<CurrentlyShowModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_currently_reading, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         db = FirebaseFirestore.getInstance()
        postArrayList = ArrayList()


        rcv = view.findViewById(R.id.rcv)

        adapter = CurrentlyShowAdapter(postArrayList)
        rcv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        rcv.adapter = adapter


        db.collection("CurrentlyBooks").addSnapshotListener { value, error ->

            if (error != null) {
                //hataa
                Toast.makeText(requireContext()," HATA ", Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    val documents = value.documents
                    for (documentss in documents) {



                        val bookName = documentss.get("bookName") as? String
                        val pageCount = (documentss.get("pageCount") as? Long)?.toInt() ?: 0
                        val readDays = (documentss.get("readDays") as? Long)?.toInt() ?: 0
                        val post = CurrentlyShowModel(bookName ?: "", pageCount, readDays)

                        postArrayList.add(post)
                    }
                    adapter.notifyDataSetChanged()
                }

            }


        }









    }

}