package com.gsrajeni.appscheduler.core.di

import com.gsrajeni.appscheduler.core.service.MyAlarmManager
import com.gsrajeni.appscheduler.data.sources.InstalledAppDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {
    @Singleton
    @Provides
    fun provideInstalledAppDataSource(): InstalledAppDataSource {
        return InstalledAppDataSource()
    }
    @Singleton
    @Provides
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    @Singleton
    @Provides
    fun getAlarmManager(): MyAlarmManager {
        return MyAlarmManager()
    }
}