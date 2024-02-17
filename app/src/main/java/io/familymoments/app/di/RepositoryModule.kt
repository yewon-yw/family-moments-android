package io.familymoments.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.familymoments.app.core.network.api.AuthService
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSource
import io.familymoments.app.core.network.datasource.UserInfoPreferencesDataSourceImpl
import io.familymoments.app.core.network.repository.AuthRepository
import io.familymoments.app.core.network.repository.impl.AuthRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    private const val USER_INFO_PREFERENCES_KEY = "user_info"

    @Provides
    @Singleton
    fun provideAuthRepository(authService: AuthService, userInfoPreferencesDataSource: UserInfoPreferencesDataSource): AuthRepository {
        return AuthRepositoryImpl(authService, userInfoPreferencesDataSource)
    }

    @Provides
    @Singleton
    fun provideTokenPreferencesDataSource(@ApplicationContext context: Context): UserInfoPreferencesDataSource {
        val preferences = context.getSharedPreferences(USER_INFO_PREFERENCES_KEY, Context.MODE_PRIVATE)
        return UserInfoPreferencesDataSourceImpl(preferences)
    }
}
