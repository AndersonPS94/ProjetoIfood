package com.example.loja.data.remote.firebase.repository.produto

import com.example.core.UIStatus
import com.example.loja.domain.model.Produto

interface IProdutoRepository {
    suspend fun salvar(
        produto: Produto,
        uiStatus: (UIStatus<String>)-> Unit
    )

    suspend fun atualizar(
        produto: Produto,
        uiStatus: (UIStatus<String>)-> Unit
    )

    suspend fun listar (
        uiStatus: (UIStatus<List<Produto>>) -> Unit
    )

    suspend fun recuperarProdutoPeloId(
        idProduto: String,
        uiStatus: (UIStatus<Produto>) -> Unit
    )

    suspend fun remover(
        idProduto: String,
        uiStatus: (UIStatus<Boolean>) -> Unit
    )
}