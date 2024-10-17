package com.mahmutgunduz.jeybook.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.CardInfoModel2
import com.mahmutgunduz.jeybook.Model.CardİnfoModel
import com.mahmutgunduz.jeybook.databinding.RecyclerRowSecondBinding

class MultiViewAdapter2(val list: ArrayList<CardInfoModel2>) : RecyclerView.Adapter<MultiViewAdapter2.MultiViewViewHolder>() {
    class MultiViewViewHolder(val binding: RecyclerRowSecondBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultiViewAdapter2.MultiViewViewHolder {
        val binding = RecyclerRowSecondBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MultiViewViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MultiViewAdapter2.MultiViewViewHolder, position: Int) {
        //holder.binding.txtBig.text = list[position].txt
    }

    override fun getItemCount(): Int {
        return list.size
    }
}