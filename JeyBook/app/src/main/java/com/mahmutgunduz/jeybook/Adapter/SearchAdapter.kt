package com.mahmutgunduz.jeybook.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.mahmutgunduz.jeybook.Model.BookItem
import com.mahmutgunduz.jeybook.R
import com.mahmutgunduz.jeybook.View.BookDetailsActivity
import com.mahmutgunduz.jeybook.databinding.RecyclerRowSearchBinding
import com.squareup.picasso.Picasso

class SearchAdapter(private val searchList: ArrayList<BookItem>) :
    RecyclerView.Adapter<SearchAdapter.SearchHolder>() {

    class SearchHolder(private val binding: RecyclerRowSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: BookItem) {
            val thumbnailUrl = item.volumeInfo.imageLinks?.thumbnail ?: "No Thumbnail"
            if (thumbnailUrl != null){
                val title = item.volumeInfo.title
                val searchRowAuthor = item.volumeInfo.authors?.joinToString(", ") ?: "Bilinmiyor."
                val searchRowLanguage = item.volumeInfo.language ?: "Dil bilinmiyor."
                val searchRowPageCount = item.volumeInfo.pageCount?.toString() ?: "Bilinmiyor."
                val searchRowPublishDate = item.volumeInfo.publishedDate ?: "Bilinmiyor."


                binding.searchRowAuthor.text = ("")+searchRowAuthor
                binding.searchRowLanguage.text = "Dil: $searchRowLanguage"
                binding.searchRowPublishDate.text = "Yayın Tarihi: $searchRowPublishDate"
                binding.searchRowPageCount.text = "Sayfa sayısı: $searchRowPageCount"


                binding.cardView.setOnClickListener {






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
                binding.searchRowTitle.text = title

            }else{

                // Thumbnail URL null ise boş bir resim veya placeholder resmi kullan

            }




            Log.d("SearchAdapter", "Thumbnail URL: $thumbnailUrl")


            if (thumbnailUrl != null) {
                Picasso.get()
                    .load(thumbnailUrl)
                    .placeholder(R.drawable.load)
                    .error(R.drawable.load)
                    .into(binding.imgDesign)





            } else {
                // Thumbnail URL null ise boş bir resim veya placeholder resmi kullan
                binding.imgDesign.setImageResource(R.drawable.load)
            }
        }
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val binding =
            RecyclerRowSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchHolder(binding)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(searchList[position])




    }

    fun updateData(newList: List<BookItem>) {
        searchList.clear()
        searchList.addAll(newList)
        notifyDataSetChanged()
    }
}
