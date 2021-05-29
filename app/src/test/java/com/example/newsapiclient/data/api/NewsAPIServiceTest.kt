package com.example.newsapiclient.data.api

import com.google.common.truth.Truth.assertThat
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class NewsAPIServiceTest {
    private lateinit var service: NewsAPIService
    private lateinit var server: MockWebServer

    @ExperimentalSerializationApi
    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(Json {
                ignoreUnknownKeys = true
                isLenient = true
            }.asConverterFactory("application/json".toMediaType()))
            .build().create(NewsAPIService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }


    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=75fc63a275074e57add4c03bbb86a2d2")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody?.articles
            assertThat(articleList?.size).isEqualTo(20)
        }
    }
    @Test
    fun getTopHeadlines_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("newsresponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articleList = responseBody?.articles
            val article = articleList?.get(0)
            assertThat(article?.author).isEqualTo("Amanda Kooser")
            assertThat(article?.url).isEqualTo("https://www.cnet.com/news/first-images-from-china-tianwen-1-mars-rover-might-take-some-time-to-reach-earth/")
            assertThat(article?.publishedAt).isEqualTo("2021-05-18T03:37:00Z")


        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}