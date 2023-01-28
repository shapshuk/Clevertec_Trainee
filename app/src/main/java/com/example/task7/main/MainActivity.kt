package com.example.task7.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.task7.R
import com.example.task7.databinding.ActivityMainBinding
import com.example.task7.retrofit.FormDefinition
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        appComponent.inject(this)

        viewModel.getViewFromApi()
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewFromApi.observe(this) {
            setUpView(it)
        }
        viewModel.fetchingError.observe(this) {
            handleFetchingError(it)
        }

    }

    override fun onStop() {
        Log.e("onStop", "Activity called onStop")
        super.onStop()
    }

    private fun restoreView() {

    }

    private fun setUpView(formDefinition: FormDefinition) {
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        title = formDefinition.title
        Glide.with(this).load(formDefinition.image).into(binding.clevertecLogo)
        recyclerView.adapter = RecyclerViewAdapter(formDefinition, viewModel)
        binding.progressBar.visibility = ProgressBar.INVISIBLE
    }

    private fun handleFetchingError(t: Throwable) {
        Log.e("Retrofit", t.toString())
        Toast.makeText(
            this,
            R.string.retrofit_error,
            Toast.LENGTH_LONG
        ).show()
    }
}