package com.example.newsapiclient.domain.usecase

import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.util.Resource
import com.example.newsapiclient.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country:String, page:Int): Resource<APIResponse> {
        return newsRepository.getNewsHeadlines(country, page)
    }
}