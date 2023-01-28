package com.example.task7.retrofit

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RetrofitService {
    @GET("meta")
    fun getView() : Observable<FormDefinition>

    @POST("data/")
    fun postData(@Body formData: FormData) : Observable<PostResponse>
}