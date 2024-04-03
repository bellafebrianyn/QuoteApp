package com.example.quoteapp.data.repository

import com.example.quoteapp.data.datasource.QuoteDataSource
import com.example.quoteapp.data.mapper.toQuotes
import com.example.quoteapp.data.model.Quote
import com.example.quoteapp.utils.ResultWrapper
import com.example.quoteapp.utils.proceedFlow
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>>
}

class QuoteRepositoryImpl(private val dataSource: QuoteDataSource) : QuoteRepository {
    override fun getRandomQuotes(): Flow<ResultWrapper<List<Quote>>> {
        return proceedFlow { dataSource.getRandomQuotes().toQuotes() }
    }

}