package com.example.loja.domain.model

import android.net.Uri

data class UploadLoja(
    val nomeLoja: String,
    val nomeImagem: String,
    val uriImagem: Uri
)
