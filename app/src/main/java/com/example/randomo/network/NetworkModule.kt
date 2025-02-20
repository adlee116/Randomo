package com.example.randomo.network

import com.example.loginandregistration.presentation.network.PoliceService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Named(ModuleCompanion.OKHTTP_DEFAULT)
    fun okhttpDefault(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Provides
    fun retrofitBuilder(
        @Named(ModuleCompanion.OKHTTP_DEFAULT) okhttp: OkHttpClient
    ): Retrofit.Builder {
        val mapper = ObjectMapper().registerModule(KotlinModule())
        return Retrofit.Builder()
            .baseUrl(ModuleCompanion.MAIN_URL)
            .addConverterFactory(JacksonConverterFactory.create(mapper))
            .client(okhttp)
    }

    @Provides
    @Singleton
    fun providePoliceService(retrofitBuilder: Retrofit.Builder): PoliceService {
        val service = retrofitBuilder.build()
        return service.create(PoliceService::class.java)
    }


}