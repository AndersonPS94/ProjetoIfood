package com.example.loja.domain.model

data class Loja(
    val idLoja: String = "",
    val idCategoria : String = "" ,
    val categoria: String = "",
    val nome: String = "",
    val razaoSocial: String = "",
    val cnpj: String = "",
    val sobreEmpresa: String = "",
    val telefone: String = "",
    val urlPerfil: String = "",
    val urlCapa: String = "",
)
