package com.acompany.lift.data.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SerializationModule {

    @Provides
    @Singleton
    fun provideRfcDateAdapter(): Rfc3339DateJsonAdapter {
        return Rfc3339DateJsonAdapter()
    }

    @Provides
    @Singleton
    fun provideMoshi(rfc3339DateJsonAdapter: Rfc3339DateJsonAdapter): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(Date::class.java, rfc3339DateJsonAdapter)
            .build()
    }
}