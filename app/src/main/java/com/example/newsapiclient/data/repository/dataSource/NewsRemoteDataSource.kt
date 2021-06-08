package com.example.newsapiclient.data.repository.dataSource

import com.example.newsapiclient.data.model.APIResponse
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country:String, page:Int): Response<APIResponse>
    suspend fun getSearchedTopHeadlines(country:String, searchQuery:String, page:Int): Response<APIResponse>
}