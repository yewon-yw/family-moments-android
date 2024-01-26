package io.familymoments.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.familymoments.app.network.LoginService
import io.familymoments.app.repository.LoginRepository
import io.familymoments.app.repository.TokenRepository
import io.familymoments.app.repository.impl.LoginRepositoryImpl
import io.familymoments.app.repository.impl.TokenRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun provideLoginRepository(loginService: LoginService, tokenRepository: TokenRepository): LoginRepository {
        return LoginRepositoryImpl(loginService, tokenRepository)
    }

    @Provides
    @Singleton
    fun provideTokenRepository(@ApplicationContext context: Context): TokenRepository {
        return TokenRepositoryImpl(context)
    }

}
