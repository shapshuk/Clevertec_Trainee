package com.example.task7.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        // hides keyboard every time the Activity starts
        this.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        viewModel.postResult.observe(this) {
            showResultDialog(it)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.viewFromApi.observe(this) {
            setUpView(it)
        }
        viewModel.fetchingError.observe(this) {
            handleFetchingError(it)
        }
        viewModel.progressBarVisibility.observe(this) {
            binding.progressBar.visibility = it
        }
    }

    private fun showResultDialog(result: String) {
        viewModel.progressBarVisibility.value = ProgressBar.INVISIBLE
        AlertDialog.Builder(this)
            .setMessage(result)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun setUpView(formDefinition: FormDefinition) {
        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        title = formDefinition.title
        Glide.with(this).load(formDefinition.image).into(binding.clevertecLogo)
        recyclerView.adapter = RecyclerViewAdapter(formDefinition, viewModel)
        viewModel.progressBarVisibility.value = ProgressBar.INVISIBLE
    }

    private fun handleFetchingError(t: Throwable) {
        viewModel.progressBarVisibility.value = ProgressBar.INVISIBLE
        Log.e("Retrofit", t.toString())
        Toast.makeText(
            this,
            R.string.retrofit_error,
            Toast.LENGTH_LONG
        ).show()
    }
}