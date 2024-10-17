package com.mahmutgunduz.jeybook.Adapter.DiscoverRcv

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookDetailsActivity
import com.mahmutgunduz.jeybook.databinding.RecyclerRow2Binding
import com.squareup.picasso.Picasso

class HorizontalAdapter2(public var list1: List<BookItem>) : RecyclerView.Adapter<HorizontalAdapter2.RowHolder>() {

    inner class RowHolder(private val binding: RecyclerRow2Binding) : RecyclerView.ViewHolder(binding.root) {
        // ViewHolder içindeki elemanları bağlama
        fun bind(item: BookItem) {
            binding.txtRow2.text = item.volumeInfo.title
            // Picasso ile resmi yükleme işlemi
            // ImageLinks null olabilir, o yüzden kontrol yapıyoruz
            val thumbnailUrl = item.volumeInfo.imageLinks?.thumbnail
            if (thumbnailUrl != null) {
                Picasso.get().load(thumbnailUrl)
                    .error(R.drawable.load) // Eğer resim yüklenemezse gösterilecek placeholder
                    .into(binding.imgRow2)
            } else {
                // Eğer thumbnail boş ise, bir placeholder gösteriyoruz
                binding.imgRow2.setImageResource(R.drawable.load)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        // ViewHolder oluşturma
        val binding = RecyclerRow2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowHolder(binding)
    }

    override fun getItemCount(): Int {
        // Liste eleman sayısını döndürme
        return list1.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        val bookItem = list1[position]
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

    fun updateData2(newList: List<BookItem>) {
        // Veriyi güncelleme ve RecyclerView'e bildirme
        list1 = newList
        notifyDataSetChanged()
    }
}
