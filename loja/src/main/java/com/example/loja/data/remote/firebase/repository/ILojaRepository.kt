package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus

interface ILojaRepository {
    suspend fun atualizarCampo(
        campo: Map<String,Any>,
        uiStatus: (UIStatus<Boolean>) -> Unit
        ){

    }
}