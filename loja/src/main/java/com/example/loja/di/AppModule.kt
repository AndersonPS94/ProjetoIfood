package com.example.loja.di

import com.example.loja.data.remote.firebase.repository.AutenticacaoRepositoryImpl
import com.example.loja.data.remote.firebase.repository.IAutenticacaoRepository
import com.example.loja.domain.usecase.AutenticacaoUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Provides
    fun provideAutenticacaoUseCase(): AutenticacaoUseCase {
        return AutenticacaoUseCase()
    }

    @Provides
    fun provideAutenticacaoRepository(
        firebaseAuth: FirebaseAuth
    ): IAutenticacaoRepository {
        return AutenticacaoRepositoryImpl(firebaseAuth)
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()

    }
}