package com.example.aulaifood.domain.usecase

import com.example.aulaifood.domain.model.Usuario
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator

class AutenticacaoUseCase {

    fun validarCadastroUsuario(usuario: Usuario) : ResultadoValidacao{

        val resultadoValidacao = ResultadoValidacao()
        val valNome = usuario.nome.validator()
            .minLength(6)
            .check()

        val valEmail = usuario.email.validator()
            .validEmail()
            .check()

        val valSenha = usuario.senha.validator()
            .minLength(6)
            .check()

        val valTelefone = usuario.telefone.validator()
            .minLength(14)
            .check()

        if (valNome)
            resultadoValidacao.nome = true
        if (valEmail)
            resultadoValidacao.email = true
        if (valSenha)
            resultadoValidacao.senha = true
        if (valTelefone)
            resultadoValidacao.telefone = true

        return resultadoValidacao
    }

    fun validarloginUsuario(usuario: Usuario) : ResultadoValidacao{

        val resultadoValidacao = ResultadoValidacao()
        val valEmail = usuario.email.validator()
            .validEmail()
            .check()

        val valSenha = usuario.senha.validator()
            .minLength(6)
            .check()

        if (valEmail)
            resultadoValidacao.email = true
        if (valSenha)
            resultadoValidacao.senha = true

        return resultadoValidacao
    }
}