package com.gsrajeni.appscheduler.core.di

import com.gsrajeni.appscheduler.data.sources.InstalledAppDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Provides
    fun provideInstalledAppDataSource(): InstalledAppDataSource {
        return InstalledAppDataSource()
    }
}