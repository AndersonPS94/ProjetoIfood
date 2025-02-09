package com.example.loja.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UIStatus
import com.example.loja.data.remote.firebase.repository.UploadRepository
import com.example.loja.domain.model.UploadLoja
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LojaViewModel @Inject constructor(
    private val uploadRepository: UploadRepository
): ViewModel() {

    fun uploadImagem(
        uploadLoja: UploadLoja,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {
        uiStatus.invoke(UIStatus.carregando)
            viewModelScope.launch {
                val upload = async {
                    uploadRepository.upload(
                        uploadLoja.nomeImagem, uploadLoja.nomeImagem, uploadLoja.uriImagem
                    )
                }
                val uiStatusUpload = upload.await()
                if (uiStatusUpload is UIStatus.Sucesso) {
                    val urlImagem = uiStatusUpload.dados
                    uiStatus.invoke(UIStatus.Sucesso(true))
                }else {
                    uiStatus.invoke(UIStatus.Erro("Erro ao fazer upload da imagem"))
                }
       }
    }
}