package com.mahmutgunduz.jeybook.View

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahmutgunduz.jeybook.databinding.ActivityBookDetailsBinding
import com.squareup.picasso.Picasso

class BookDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookDetailsBinding
    private var bookTitle: String? = null
    private var bookImageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityBookDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        bookTitle = intent.getStringExtra("bookTitle")
        bookImageUrl = intent.getStringExtra("bookImageUrl")
        val language = intent.getStringExtra("language")
        val categoriess = intent.getStringArrayListExtra("categories")
        val authors = intent.getStringArrayListExtra("authors")
        val publisher = intent.getStringExtra("publisher")
        val description = intent.getStringExtra("description")
        val count = intent.getIntExtra("pageCount", 0)

        // Verileri View'lara atıyoruz
        if (categoriess != null) {
            binding.categoriesTxt.text = "Kategori: ${categoriess.toString()}"
        } else {
            Toast.makeText(this@BookDetailsActivity, "Kategori bilgisi yok", Toast.LENGTH_LONG).show()
        }

        binding.publisherTxt.text = "Yayinlayan: ${publisher ?: "Bilinmiyor..."}"
        binding.aciklamaTxt.text = description ?: "Bilinmiyor..."
        binding.pageCountTxt.text = "Sayfa sayısı: $count"
        binding.languageTxt.text = "Dil: ${language ?: "Bilinmiyor..."}"
        binding.bookAuthor.text = "Yazar: ${authors?.toString() ?: "Bilinmiyor..."}"
        binding.bookTitle.text = bookTitle ?: "Bilinmiyor..."


        Picasso.get().load(bookImageUrl).into(binding.bookImage)


        binding.readStartButton.setOnClickListener {
            val intent = Intent(this@BookDetailsActivity, BookLoadActivity::class.java)
            intent.putExtra("bookTitle", bookTitle)
            intent.putExtra("bookImageUrl", bookImageUrl)
            startActivity(intent)
        }
    }
}
