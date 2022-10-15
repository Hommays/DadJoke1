package com.example.dadjoke1

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

open class FragmentViewModel : ViewModel() {
    val dadJokeService by lazy {
        Retrofit.Builder()
            .baseUrl("https://dadjokes.io/documentation/endpoints/random-jokes")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(DadJokeService::class.java)
    }
}