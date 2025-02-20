package com.example.aulaifood.domain.model


data class Usuario(
    val email: String = "",
    val senha: String = "",
    val nome: String = "",
    val telefone: String = "",
    val urlPerfil: String = "",
    var id: String = "",
){
    fun mapToUsuarioFirestore() : Map<String, Any>{
        val dados = mutableMapOf<String, Any>()

        if( id.isNotEmpty() ) dados["id"] = id
        if( email.isNotEmpty() ) dados["email"] = email
        if( nome.isNotEmpty() ) dados["nome"] = nome
        if( telefone.isNotEmpty() ) dados["telefone"] = telefone
        if( urlPerfil.isNotEmpty() ) dados["urlPerfil"] = urlPerfil

        return dados
    }
}
