package com.example.aulaifood.data.remote.firebase.repository

import com.example.aulaifood.domain.model.Usuario
import com.example.core.UIStatus

interface IAutenticacaoRepository {
    suspend fun cadastrarUsuario (
        usuario: Usuario,
        uiStatus: (UIStatus<Boolean>) -> Unit
    )
    suspend fun logarUsuario (
        usuario: Usuario,
        uiStatus: (UIStatus<Boolean>) -> Unit
    )
    fun verificarUsuarioLogado() : Boolean
}