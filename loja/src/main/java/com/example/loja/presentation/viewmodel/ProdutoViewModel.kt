package com.example.loja.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UIStatus
import com.example.loja.data.remote.firebase.repository.produto.IProdutoRepository
import com.example.loja.data.remote.firebase.repository.upload.UploadRepository
import com.example.loja.domain.model.Produto
import com.example.loja.domain.model.UploadStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutoViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val produtoRepositoryImpl: IProdutoRepository
) : ViewModel() {

    fun remover(
        idProduto: String,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ){
        
        viewModelScope.launch {
            produtoRepositoryImpl.remover(idProduto, uiStatus)
        }
    }

    fun recuperarProdutoPeloId(
        idProduto: String,
        uiStatus: (UIStatus<Produto>) -> Unit
    ){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
                produtoRepositoryImpl.recuperarProdutoPeloId(idProduto, uiStatus)
        }
    }

    fun listar(uiStatus : (UIStatus<List<Produto>>) -> Unit){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            produtoRepositoryImpl.listar(uiStatus)
        }
    }

    fun salvar(produto: Produto,
               uiStatus: (UIStatus<String>) -> Unit
    ) {
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            if (produto.id.isEmpty()) {
                produtoRepositoryImpl.salvar(produto, uiStatus)
            } else {
                produtoRepositoryImpl.atualizar(produto, uiStatus)
            }
        }

    }
    fun uploadImagem(
        uploadStorage: UploadStorage,
        idProduto: String,
        uiStatus: (UIStatus<String>) -> Unit
    ) {
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            val upload = async {
                uploadRepository.upload(
                    uploadStorage.nomeImagem, uploadStorage.nomeImagem, uploadStorage.uriImagem
                )
            }
            val uiStatusUpload = upload.await()
            if (uiStatusUpload is UIStatus.Sucesso) {
                val urlImagem = uiStatusUpload.dados

                val produto = Produto(
                    id = idProduto, url = urlImagem
                )

                if(idProduto.isEmpty()){//Salvar produto
                        produtoRepositoryImpl.salvar(produto, uiStatus)
                }else {//Atualizar produto
                        produtoRepositoryImpl.atualizar(produto, uiStatus)
                }
            }else {
                uiStatus.invoke(UIStatus.Erro("Erro ao fazer upload da imagem"))
            }
        }
    }
}