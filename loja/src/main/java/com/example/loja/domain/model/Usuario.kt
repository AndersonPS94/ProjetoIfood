package com.example.loja.domain.model

data class Usuario(
    val email: String,
    val senha: String,
    val nome: String = "",
    val telefone: String = ""
)
