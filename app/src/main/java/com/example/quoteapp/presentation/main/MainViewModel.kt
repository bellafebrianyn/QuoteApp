package com.example.quoteapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.quoteapp.data.repository.QuoteRepository
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val repository: QuoteRepository): ViewModel() {
    val quotesData = repository.getRandomQuotes().asLiveData(Dispatchers.IO)
}