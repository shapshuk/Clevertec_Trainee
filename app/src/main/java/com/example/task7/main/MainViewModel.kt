package com.example.task7.main

import android.widget.ProgressBar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task7.retrofit.FormData
import com.example.task7.retrofit.FormDefinition
import com.example.task7.retrofit.RetrofitService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val retrofitService: RetrofitService): ViewModel() {

    private val _viewFromApi = MutableLiveData<FormDefinition>()
    val viewFromApi = _viewFromApi

    private val _fetchingError = MutableLiveData<Throwable>()
    val fetchingError = _fetchingError

    private val _postResult = MutableLiveData<String>()
    val postResult = _postResult

    val formData: MutableMap<String, String> = mutableMapOf()

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility = _progressBarVisibility

    private lateinit var viewFromApiDisposable: Disposable
    private lateinit var postResultDisposable: Disposable

    fun getViewFromApi() {
        progressBarVisibility.value = ProgressBar.VISIBLE
        val apiObservable = retrofitService.getView()
        viewFromApiDisposable = apiObservable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _viewFromApi.value = it },
                { _fetchingError.value = it },
            )
    }

    fun postData() {
        progressBarVisibility.value = ProgressBar.VISIBLE
        val postResultObservable = retrofitService.postData(FormData(formData))
        postResultDisposable = postResultObservable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { postResult.value = it.result },
                { _fetchingError.value = it }
            )
    }

    override fun onCleared() {
        viewFromApiDisposable.dispose()
        postResultDisposable.dispose()
        super.onCleared()
    }
}