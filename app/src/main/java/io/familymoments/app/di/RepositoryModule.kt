package io.familymoments.app.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.familymoments.app.network.LoginService
import io.familymoments.app.repository.LoginRepository
import io.familymoments.app.repository.impl.LoginRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(loginService: LoginService): LoginRepository {
        return LoginRepositoryImpl(loginService)
    }

}
