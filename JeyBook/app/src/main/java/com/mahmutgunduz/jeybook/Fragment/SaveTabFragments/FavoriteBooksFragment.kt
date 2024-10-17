package com.mahmutgunduz.jeybook.Fragment.SaveTabFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mahmutgunduz.jeybook.R


class FavoriteBooksFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favorite_books, container, false)
        return view
    }

}