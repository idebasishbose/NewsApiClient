package com.example.newsapiclient.presentation.di

import android.app.Application
import androidx.room.Room
import com.example.newsapiclient.data.db.ArticleDAO
import com.example.newsapiclient.data.db.ArticleDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesNewsDatabase(app: Application): ArticleDataBase {
        return Room.databaseBuilder(app, ArticleDataBase::class.java, "news_db")
            .fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun providesNewsDAO(articleDataBase: ArticleDataBase): ArticleDAO {
        return articleDataBase.getArticleDAO()
    }

}