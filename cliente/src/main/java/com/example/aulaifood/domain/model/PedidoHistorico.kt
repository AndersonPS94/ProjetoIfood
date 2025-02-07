package com.example.aulaifood.domain.model

data class PedidoHistorico(
    val data: String ="",
    val nomeLoja: String ="",
    val urlLoja: String ="",
    val itens : List<String> = listOf(),
)
