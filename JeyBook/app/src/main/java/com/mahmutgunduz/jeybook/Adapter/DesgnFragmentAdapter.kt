package com.mahmutgunduz.jeybook.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookDetailsActivity
import com.mahmutgunduz.jeybook.databinding.DesgnRcvRowBinding
import com.squareup.picasso.Picasso
class DesgnFragmentAdapter(var listee: List<BookItem>) : RecyclerView.Adapter<DesgnFragmentAdapter.ViewHolder>() {

    class ViewHolder(val binding: DesgnRcvRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: BookItem) {
            // Null kontrolü ile resmi yükleme işlemi
            val thumbnailUrl = item.volumeInfo.imageLinks?.thumbnail
            if (thumbnailUrl != null) {
                Picasso.get().load(thumbnailUrl).into(binding.imgDesign)
                binding.searchRowTitle.text = item.volumeInfo.title


                // Yazar bilgisi. Eğer authors listesi varsa virgülle birleştiriliyor, yoksa boş string atanıyor.
                val authors = item.volumeInfo.authors?.joinToString(", ") ?: ""
                binding.searchRowAuthor.text = if (authors.isNotEmpty()) "$authors" else "Yazar: Bilinmiyor"


                val language = item.volumeInfo.language ?: ""
                binding.searchRowLanguage.text = if (language.isNotEmpty()) "Dil: $language" else "Dil: Bilinmiyor"


                val pageCount = item.volumeInfo.pageCount?.toString() ?: ""
                binding.searchRowPageCount.text = if (pageCount.isNotEmpty()) "Sayfa sayısı: $pageCount" else "Sayfa sayısı: Bilinmiyor"


                val publishDate = item.volumeInfo.publishedDate ?: ""
                binding.searchRowPublishDate.text = if (publishDate.isNotEmpty()) "Yayın tarihi: $publishDate" else "Yayın tarihi: Bilinmiyor"



                binding.cardView.setOnClickListener{
                    val intent = Intent(binding.root.context, BookDetailsActivity::class.java)


                    intent.putExtra("bookTitle", item.volumeInfo.title)
                    intent.putExtra("bookImageUrl", item.volumeInfo.imageLinks.thumbnail)


                    intent.putExtra("language",item.volumeInfo.language)
                    intent.putExtra("description",item.volumeInfo.description)
                    intent.putExtra("publisher",item.volumeInfo.publisher)

                    intent.putExtra("pageCount",item.volumeInfo.pageCount)

                    intent.putStringArrayListExtra("authors",ArrayList<String>(item.volumeInfo.authors))

                    val categoriesList = if (item.volumeInfo.categories != null) {
                        ArrayList<String>(item.volumeInfo.categories)
                    } else {
                        ArrayList()  // Null ise boş bir liste gönderiyoruz
                    }



                    intent.putStringArrayListExtra("categories", categoriesList)




                    binding.root.context.startActivity(intent)


                    Toast.makeText(binding.root.context, "Tıklandı", Toast.LENGTH_SHORT).show()



                }
            } else {

                binding.imgDesign.setImageResource(R.drawable.load)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DesgnRcvRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listee.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listee[position]
        holder.bind(item)


    }

    fun updateDataDesgn(newList: List<BookItem>) {
        listee = newList
        notifyDataSetChanged()
    }
}
