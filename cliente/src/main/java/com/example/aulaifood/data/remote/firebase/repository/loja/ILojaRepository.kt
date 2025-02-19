package com.example.aulaifood.data.remote.firebase.repository.loja

import com.example.aulaifood.domain.model.Loja
import com.example.core.UIStatus


interface ILojaRepository {


    suspend fun recuperarDadosLoja(
        idLoja: String,
        uiStatus: (UIStatus<Loja>) -> Unit
    )
    suspend fun listar(
        uiStatus: (UIStatus<List<Loja>>) -> Unit
    )

}