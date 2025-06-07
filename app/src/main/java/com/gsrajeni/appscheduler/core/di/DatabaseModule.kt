package com.gsrajeni.appscheduler.core.di

import android.content.Context
import androidx.room.Room
import com.gsrajeni.appscheduler.data.room.AppDatabase
import com.gsrajeni.appscheduler.data.sources.SharedPreferenceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun getRoomDatabase(@ApplicationContext context: Context): AppDatabase {
        val room = Room.databaseBuilder(
            context, AppDatabase::class.java, "schedule-database"
        ).build()
        return room
    }

    @Provides
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferenceDataSource {
        return SharedPreferenceDataSource(context)
    }
}