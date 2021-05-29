package com.example.newsapiclient.data.repository.dataSourceImlp

import com.example.newsapiclient.data.api.NewsAPIService
import com.example.newsapiclient.data.model.APIResponse
import com.example.newsapiclient.data.repository.dataSource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsAPIService: NewsAPIService
) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<APIResponse> {
        return newsAPIService.getTopHeadlines(country, page)
    }
}