package com.example.denettest.di

import android.content.Context
import com.example.denettest.util.FileResolver
import com.example.denettest.util.JsonResolver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFileResolver(@ApplicationContext appContext: Context): FileResolver {
        return FileResolver(appContext)
    }

    @Provides
    @Singleton
    fun provideJsonResolver(): JsonResolver {
        return JsonResolver()
    }
}