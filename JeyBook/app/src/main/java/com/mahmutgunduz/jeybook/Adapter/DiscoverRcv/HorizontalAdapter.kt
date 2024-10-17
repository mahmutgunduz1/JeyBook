package com.mahmutgunduz.jeybook.Adapter.DiscoverRcv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookDetailsActivity
import com.mahmutgunduz.jeybook.databinding.RecyclerRowBinding
import com.squareup.picasso.Picasso



class HorizontalAdapter(private var list: List<BookItem>) : RecyclerView.Adapter<HorizontalAdapter.RowHolder>() {

    inner class RowHolder(private val binding: RecyclerRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookItem) {
            binding.txtRow.text = item.volumeInfo.title

            val thumbnailUrl = item.volumeInfo.imageLinks?.thumbnail
            if (thumbnailUrl != null) {
                Picasso.get().load(thumbnailUrl)
                    .error(R.drawable.load) // Eğer resim yüklenemezse gösterilecek placeholder
                    .into(binding.imgRow)
            } else {
                // Eğer thumbnail boş ise, bir placeholder gösteriyoruz
                binding.imgRow.setImageResource(R.drawable.load)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val bookItem = list[position]
        holder.bind(bookItem)





        holder.itemView.setOnClickListener(View.OnClickListener {


            val intent = Intent(holder.itemView.context, BookDetailsActivity::class.java)


            intent.putExtra("bookTitle", bookItem.volumeInfo.title)
            intent.putExtra("bookImageUrl", bookItem.volumeInfo.imageLinks.thumbnail)


            intent.putExtra("language",bookItem.volumeInfo.language)
            intent.putExtra("description",bookItem.volumeInfo.description)
            intent.putExtra("publisher",bookItem.volumeInfo.publisher)

            intent.putExtra("pageCount",bookItem.volumeInfo.pageCount)

            intent.putStringArrayListExtra("authors",ArrayList<String>(bookItem.volumeInfo.authors))

            val categoriesList = if (bookItem.volumeInfo.categories != null) {
                ArrayList<String>(bookItem.volumeInfo.categories)
            } else {
                ArrayList()  // Null ise boş bir liste gönderiyoruz
            }



            intent.putStringArrayListExtra("categories", categoriesList)

            // Activity'i başlat
            holder.itemView.context.startActivity(intent)


        })
    }

    fun updateData(newList: List<BookItem>) {
        list = newList
        notifyDataSetChanged()
    }
}
