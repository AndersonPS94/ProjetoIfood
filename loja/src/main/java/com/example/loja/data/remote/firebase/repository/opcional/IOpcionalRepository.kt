package com.example.loja.data.remote.firebase.repository.opcional

import com.example.core.UIStatus
import com.example.loja.domain.model.Opcional


interface IOpcionalRepository {

    suspend fun salvar(
        opcional: Opcional,
        uiStatus: (UIStatus<String>) -> Unit
    )

    suspend fun listar(
        idProduto: String,
        uiStatus: (UIStatus<List<Opcional>>) -> Unit
    )

    suspend fun remover(
        opcional: Opcional,
        uiStatus: (UIStatus<Boolean>) -> Unit
    )
}