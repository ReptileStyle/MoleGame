package com.example.molegame.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.molegame.core.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    fun provideAppContext(@ApplicationContext context: Application): Context {
        return context.applicationContext
    }
    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.sharedPreferencesName,Context.MODE_PRIVATE)
    }
}