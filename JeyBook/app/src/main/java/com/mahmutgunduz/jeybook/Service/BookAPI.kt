package com.mahmutgunduz.jeybook.Service

import com.mahmutgunduz.jeybook.Model.BooksModel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BookAPI {

    // GETT =VERİ CEKMEK , POST=VERİYİ YAZMAK DEĞİŞTİRMEK  , UPDATE =GÜNCELLE , DELETE =SİL

/*
@GET("volumes?q=android&key=AIzaSyA1iopDNSO4362aAX22JBPHzGKW9rty5eE")
fun getData(@Query("q") query: String):Call<BooksModel>
*/




  @GET("volumes")
  fun getData(
    @Query("q") query: String,
    @Query("maxResults") maxResults: Int = 40,
    @Query("key") apiKey: String = "AIzaSyA1iopDNSO4362aAX22JBPHzGKW9rty5eE"
  ): Call<BooksModel>
}