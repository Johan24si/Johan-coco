package com.example.johan_coco.Home

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

interface NewsService {

    @GET("rss/")
    fun getNews(): Call<String>

    companion object {

        private const val BASE_URL = "https://www.cnbcindonesia.com/"

        fun create(): NewsService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()

            return retrofit.create(NewsService::class.java)
        }
    }
}