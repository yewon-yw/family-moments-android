package io.familymoments.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.familymoments.app.network.JoinService
import io.familymoments.app.network.LoginService
import io.familymoments.app.repository.JoinRepository
import io.familymoments.app.repository.UserRepository
import io.familymoments.app.repository.impl.JoinRepositoryImpl
import io.familymoments.app.repository.impl.UserRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(loginService: LoginService): UserRepository {
        return UserRepositoryImpl(loginService)
    }

    @Provides
    @Singleton
    fun provideJoinRepository(joinService: JoinService): JoinRepository {
        return JoinRepositoryImpl(joinService)
    }

}