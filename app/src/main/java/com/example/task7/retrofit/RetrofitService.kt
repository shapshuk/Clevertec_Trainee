package com.example.task7.retrofit

import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitService {
    @GET("meta")
    fun getView() : Observable<FormDefinition>
}