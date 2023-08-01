package com.example.trailevy.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitInstance {


    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.43.152:3000")
        .addConverterFactory(GsonConverterFactory.create())

        .build()

    val apiService = retrofit.create(ApiService::class.java)


}



