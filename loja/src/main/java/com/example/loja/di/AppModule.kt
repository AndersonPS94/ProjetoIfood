package com.example.loja.di

import com.example.loja.data.remote.firebase.repository.AutenticacaoRepositoryImpl
import com.example.loja.data.remote.firebase.repository.IAutenticacaoRepository
import com.example.loja.data.remote.firebase.repository.UploadRepository
import com.example.loja.domain.usecase.AutenticacaoUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
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
    fun provideUploadRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): UploadRepository {
        return UploadRepository(firebaseStorage, firebaseAuth)
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()

    }

    @Provides
    fun provideFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }
}