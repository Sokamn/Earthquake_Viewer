package com.sokamn.earthquakeviewer.di

import com.sokamn.earthquakeviewer.data.remote.EarthquakeRepositoryImpl
import com.sokamn.earthquakeviewer.domain.repository.EarthquakeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindEarthquakeRepository(
        earthquakeRepositoryImpl: EarthquakeRepositoryImpl
    ): EarthquakeRepository
}