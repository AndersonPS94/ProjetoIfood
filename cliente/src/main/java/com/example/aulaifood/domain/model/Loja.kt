package com.example.aulaifood.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable {
    fun toMap(): Map<String, Any> {
        val dados = mutableMapOf<String, Any>()
        if(idLoja.isNotEmpty())     dados["idLja"]          = idLoja
        dados["idCategoria"]    = idCategoria
        dados["categoria"]      = categoria
        dados["nome"]           = nome
        dados["razaoSocial"]    = razaoSocial
        dados["cnpj"]           = cnpj
        dados["sobreEmpresa"]   = sobreEmpresa
        dados["telefone"]       = telefone
        if(urlPerfil.isNotEmpty())  dados["urlPerfil"]      = urlPerfil
        if(urlCapa.isNotEmpty())    dados["urlCapa"]        = urlCapa
        return dados
    }
}
