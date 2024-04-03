package com.example.quoteapp.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.quoteapp.R
import com.example.quoteapp.data.datasource.QuoteApiDataSource
import com.example.quoteapp.data.datasource.QuoteDataSource
import com.example.quoteapp.data.repository.QuoteRepository
import com.example.quoteapp.data.repository.QuoteRepositoryImpl
import com.example.quoteapp.data.source.network.services.QuotesApiServices
import com.example.quoteapp.databinding.ActivityMainBinding
import com.example.quoteapp.presentation.main.adapter.QuoteListAdapter
import com.example.quoteapp.utils.GenericViewModelFactory
import com.example.quoteapp.utils.proceedWhen

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by viewModels{
        val s = QuotesApiServices.invoke()
        val ds: QuoteDataSource = QuoteApiDataSource(s)
        val rp: QuoteRepository = QuoteRepositoryImpl(ds)
        GenericViewModelFactory.create(MainViewModel(rp))
    }

    private val adapter: QuoteListAdapter by lazy {
        QuoteListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setQuoteList()
        observeData()
    }

    private fun observeData() {
        viewModel.quotesData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvListQuote.isVisible = true
                    result.payload?.let { quote ->
                        adapter.submitData(quote)
                    }
                },
                doOnLoading = {
                    binding.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.rvListQuote.isVisible = false
                },
                doOnEmpty = {
                    binding.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvListQuote.isVisible = false
                    binding.layoutState.tvOnError.text = getString(R.string.text_empty)
                },
                doOnError = {
                    binding.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.rvListQuote.isVisible = false
                    binding.layoutState.tvOnError.text = result.exception?.message.orEmpty()
                },
            )
        }
    }

    private fun setQuoteList() {
        binding.rvListQuote.adapter = adapter
    }
}