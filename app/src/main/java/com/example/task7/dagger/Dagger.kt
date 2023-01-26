package com.example.task7.dagger

import com.example.task7.BuildConfig
import com.example.task7.main.MainActivity
import com.example.task7.main.MainViewModel
import com.example.task7.retrofit.RetrofitClient
import com.example.task7.retrofit.RetrofitService
import dagger.Component
import dagger.Module
import dagger.Provides

@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(activity: MainActivity)
    val mainViewModel: MainViewModel
    val retrofitService: RetrofitService
}

@Module
object AppModule {

    @Provides
    fun provideRetrofitService() : RetrofitService {
        return RetrofitClient.getClient(BuildConfig.API_URL).create(RetrofitService::class.java)
    }
    @Provides
    fun provideMainViewModel(retrofitService: RetrofitService) : MainViewModel {
        return MainViewModel(retrofitService)
    }

}