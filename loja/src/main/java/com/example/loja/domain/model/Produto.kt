package com.example.loja.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Produto(
    var id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val preco: Double = 0.0,
    val precoDestaque: Double = 0.0,
    val url: String = "",
    val emDestaque: Boolean? = null
) :  Parcelable{
    fun toMap(): Map<String, Any> {
        val dados = mutableMapOf<String, Any>()
        if(id.isNotEmpty())                             dados["id"]             = id
        if(nome.isNotEmpty())                           dados["nome"]           = nome
        if(descricao.isNotEmpty())                      dados["descricao"]      = descricao
        if(preco > 0.0)               dados["preco"]          = preco
        if(url.isNotEmpty())                            dados["url"]            = url


        emDestaque?.let {destaque ->
            dados["emDestaque"] = destaque
            dados["precoDestaque"] = if(destaque && precoDestaque > 0.0)
                                        precoDestaque else 0.0

        }
        return dados
    }
}
