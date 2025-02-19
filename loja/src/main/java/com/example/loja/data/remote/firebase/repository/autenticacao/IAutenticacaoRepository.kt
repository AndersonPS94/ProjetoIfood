package com.example.loja.data.remote.firebase.repository.autenticacao

import com.example.core.UIStatus
import com.example.loja.domain.model.Usuario

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