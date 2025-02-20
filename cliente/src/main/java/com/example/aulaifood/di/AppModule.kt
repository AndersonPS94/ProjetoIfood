package com.example.aulaifood.di

import com.example.aulaifood.data.remote.firebase.repository.autenticacao.AutenticacaoRepositoryImpl
import com.example.aulaifood.data.remote.firebase.repository.autenticacao.IAutenticacaoRepository
import com.example.aulaifood.data.remote.firebase.repository.loja.ILojaRepository
import com.example.aulaifood.data.remote.firebase.repository.loja.LojaRepositoryImpl
import com.example.aulaifood.data.remote.firebase.repository.produto.IProdutoRepository
import com.example.aulaifood.data.remote.firebase.repository.produto.ProdutoRepositoryImpl
import com.example.aulaifood.data.remote.firebase.repository.upload.UploadRepository
import com.example.aulaifood.domain.usecase.AutenticacaoUseCase
import com.example.aulaifood.domain.usecase.produto.RecuperarProdutosPorTipoUseCase
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
        return AutenticacaoRepositoryImpl(firebaseAuth,firebaseFirestore)
    }

    @Provides
    fun provideLojaRepository(
        firebaseFirestore: FirebaseFirestore
    ): ILojaRepository{
        return LojaRepositoryImpl(firebaseFirestore)
    }

    @Provides
    fun provideProdutoRepository(
        firebaseFirestore: FirebaseFirestore
    ): IProdutoRepository {
        return ProdutoRepositoryImpl(firebaseFirestore)
    }

    @Provides
    fun provideUploadRepository(
        firebaseStorage: FirebaseStorage,
        firebaseAuth: FirebaseAuth
    ): UploadRepository
    {
        return UploadRepository(firebaseStorage, firebaseAuth)
    }

    @Provides
    fun providesRecuperarProdutosPorTipoUseCase(
        produtoRepositoryImpl: IProdutoRepository
    ): RecuperarProdutosPorTipoUseCase {
        return RecuperarProdutosPorTipoUseCase(produtoRepositoryImpl)

    }


    @Provides
    fun provideFirebaseAuth() : FirebaseAuth {
        return FirebaseAuth.getInstance()

    }
    @Provides
    fun provideFirebaseFirestore() : FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }
    fun provideFirebaseStorage() : FirebaseStorage {
        return FirebaseStorage.getInstance()

    }
}