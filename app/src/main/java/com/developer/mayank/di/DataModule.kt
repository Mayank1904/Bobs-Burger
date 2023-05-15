package com.developer.mayank.di

import com.developer.data.remote.api.CharacterService
import com.developer.data.repository.CharacterRepositoryImpl
import com.developer.domain.repository.CharacterRepository
import com.developer.mayank.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideCharacterRemote(characterRemoteImpl: CharacterRepositoryImpl): CharacterRepository {
        return characterRemoteImpl
    }

    @Provides
    @Singleton
    fun provideCharacterService(): CharacterService {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .client(OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor.apply {
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                })
                .connectTimeout(Constants.OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Constants.OK_HTTP_TIMEOUT, TimeUnit.SECONDS)
            .build())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(CharacterService::class.java)
    }

}