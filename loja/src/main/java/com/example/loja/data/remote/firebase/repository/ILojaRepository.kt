package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus
import com.example.loja.domain.model.Categoria
import com.example.loja.domain.model.Loja

interface ILojaRepository {
    suspend fun atualizarCampo(
        campo: Map<String,Any>,
        uiStatus: (UIStatus<Boolean>) -> Unit
        )

    suspend fun recuperarDadosLoja( uiStatus: (UIStatus<Loja>) -> Unit)
    suspend fun recuperarCategorias( uiStatus: (UIStatus<List<Categoria>>) -> Unit)

}