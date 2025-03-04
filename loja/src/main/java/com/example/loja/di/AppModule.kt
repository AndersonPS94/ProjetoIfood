package com.example.loja.di

import com.example.loja.data.remote.firebase.repository.autenticacao.AutenticacaoRepositoryImpl
import com.example.loja.data.remote.firebase.repository.autenticacao.IAutenticacaoRepository
import com.example.loja.data.remote.firebase.repository.loja.ILojaRepository
import com.example.loja.data.remote.firebase.repository.opcional.IOpcionalRepository
import com.example.loja.data.remote.firebase.repository.produto.IProdutoRepository
import com.example.loja.data.remote.firebase.repository.loja.LojaRepositoryImpl
import com.example.loja.data.remote.firebase.repository.opcional.OpcionalRepositoryImpl
import com.example.loja.data.remote.firebase.repository.produto.ProdutoRepositoryImpl
import com.example.loja.data.remote.firebase.repository.upload.UploadRepository
import com.example.loja.domain.usecase.AutenticacaoUseCase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
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
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): IAutenticacaoRepository {
        return AutenticacaoRepositoryImpl(firebaseAuth, firebaseFirestore)
    }

    @Provides
    fun provideUploadRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): UploadRepository {
        return UploadRepository(firebaseStorage, firebaseAuth)
    }
    @Provides
    fun provideLojaRepository(
        firebaseFirestore: FirebaseFirestore,
        firebaseAuth: FirebaseAuth
    ): ILojaRepository {
        return LojaRepositoryImpl(firebaseFirestore, firebaseAuth)
    }

    @Provides
    fun provideProdutoRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore

    ): IProdutoRepository {
        return ProdutoRepositoryImpl(firebaseAuth, firebaseFirestore)
    }

    @Provides
    fun provideOpcionalRepository(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore
    ): IOpcionalRepository {
        return OpcionalRepositoryImpl(firebaseAuth, firebaseFirestore)
    }

    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()

    }

    @Provides
    fun provideFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    fun provideFirebaseFirestore () : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
}