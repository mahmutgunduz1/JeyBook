package com.mahmutgunduz.jeybook.Adapter.DiscoverRcv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookDetailsActivity
import com.mahmutgunduz.jeybook.databinding.RecyclerRow3Binding
import com.squareup.picasso.Picasso

class HorizontalAdapter3  (public var list3: List<BookItem>): RecyclerView.Adapter<HorizontalAdapter3.RowHolder>() {
    class RowHolder (private val binding: RecyclerRow3Binding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BookItem) {
            binding.txtRow3.text = item.volumeInfo.title
            val thumbnailUrl = item.volumeInfo.imageLinks?.thumbnail
            if (thumbnailUrl != null) {
                Picasso.get().load(thumbnailUrl)
                    .error(R.drawable.load) // Eğer resim yüklenemezse gösterilecek placeholder
                    .into(binding.imgRow3)
            } else {
                // Eğer thumbnail boş ise, bir placeholder gösteriyoruz
                binding.imgRow3.setImageResource(R.drawable.load)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val binding = RecyclerRow3Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
     return list3.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val bookItem = list3[position]
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

    fun updateData3(newList: List<BookItem>) {
        // Veriyi güncelleme ve RecyclerView'e bildirme
        list3 = newList
        notifyDataSetChanged()
    }
}