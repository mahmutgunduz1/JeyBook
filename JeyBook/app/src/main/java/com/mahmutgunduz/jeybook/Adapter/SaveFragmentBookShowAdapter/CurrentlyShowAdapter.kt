package com.mahmutgunduz.jeybook.Adapter.SaveFragmentBookShowAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookLoadModel
import com.mahmutgunduz.jeybook.Model.CurrentlyShowModel
import com.mahmutgunduz.jeybook.databinding.FragmentCurrentlyRcvRowBinding

class CurrentlyShowAdapter(private val bookList: List<CurrentlyShowModel>) :
    RecyclerView.Adapter<CurrentlyShowAdapter.CurrentlyShowHolder>() {

    class CurrentlyShowHolder(val binding: FragmentCurrentlyRcvRowBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrentlyShowHolder {
        val binding = FragmentCurrentlyRcvRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrentlyShowHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: CurrentlyShowHolder, position: Int) {
        val book = bookList[position]



        holder.binding.bookTitle.text = book.title
        holder.binding.pageCount.text = " sayfa sayısı : "+book.pageCount.toString()
        holder.binding.readDays.text = " okuma günü : "+book.readDays.toString()

        val okunacakGun = if (book.readDays > 0) {
            (book.pageCount / book.readDays).toString()
        } else {
            holder.binding.bookAuthor.text= " defult"
        }
        holder.binding.bookAuthor.text = "Günlük: $okunacakGun "+"sayfa"


    }
}
