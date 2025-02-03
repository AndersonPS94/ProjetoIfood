package com.example.aulaifood.domain.usecase

data class ResultadoValidacao (
    var nome: Boolean = false,
    var email: Boolean = false,
    var senha: Boolean = false,
    var telefone: Boolean = false

) {
    val sucessoValidacaoCadastro : Boolean
        get() =  nome && email && senha && telefone

    val sucessoValidacaoLogin : Boolean
        get() = email && senha
}