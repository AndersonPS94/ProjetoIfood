package com.example.aulaifood.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aulaifood.data.remote.firebase.repository.produto.IProdutoRepository
import com.example.aulaifood.domain.model.Produto
import com.example.core.UIStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProdutoViewModel @Inject constructor(
    private val produtoRepositoryImpl: IProdutoRepository
) : ViewModel() {


    /*fun recuperarProdutoPeloId(
        idProduto: String,
        uiStatus: (UIStatus<Produto>) -> Unit
    ){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
                produtoRepositoryImpl.recuperarProdutoPeloId(idProduto, uiStatus)
        }
    }*/

    fun listar(
        idLoja: String,
        uiStatus : (UIStatus<List<Produto>>) -> Unit){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            produtoRepositoryImpl.listar(idLoja,uiStatus)
        }
    }


}