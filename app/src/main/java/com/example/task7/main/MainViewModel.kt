package com.example.task7.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    private lateinit var viewFromApiDisposable: Disposable

    fun getViewFromApi() {
        val apiObservable = retrofitService.getView()

        viewFromApiDisposable = apiObservable
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { _viewFromApi.value = it },
                { _fetchingError.value = it },
            )
    }

    override fun onCleared() {
        viewFromApiDisposable.dispose()
        super.onCleared()
    }

}