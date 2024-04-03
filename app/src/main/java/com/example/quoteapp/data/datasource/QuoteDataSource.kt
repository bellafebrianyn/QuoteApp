package com.example.quoteapp.data.datasource

import com.example.quoteapp.data.source.network.model.QuoteResponse
import com.example.quoteapp.data.source.network.services.QuotesApiServices

interface QuoteDataSource {
    suspend fun getRandomQuotes(): List<QuoteResponse>
}

class QuoteApiDataSource(private val services: QuotesApiServices) : QuoteDataSource {
    override suspend fun getRandomQuotes(): List<QuoteResponse> {
        return services.getRandomQuotes()
    }

}