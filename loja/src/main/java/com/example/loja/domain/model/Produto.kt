package com.example.loja.domain.model

data class Produto(
    val nome: String = "",
    val descricao: String = "",
    val preco: String = "",
    val precoDesconto: String = "",
    val url: String = ""
)
