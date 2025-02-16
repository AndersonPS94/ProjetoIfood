package com.example.loja.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UIStatus
import com.example.loja.data.remote.firebase.repository.IOpcionalRepository
import com.example.loja.data.remote.firebase.repository.UploadRepository
import com.example.loja.domain.model.Opcional
import com.example.loja.domain.model.UploadStorage

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpcionalViewModel  @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val opcionalRepository: IOpcionalRepository
): ViewModel() {

    fun salvar(
        uploadStorage: UploadStorage,
        opcional: Opcional,
        uiStatus: (UIStatus<String>)-> Unit
    ){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            val upload = async {
                uploadRepository.upload(
                    uploadStorage.local,
                    uploadStorage.nomeImagem,
                    uploadStorage.uriImagem
                )
            }
            val uiStatusUpload = upload.await()
            if (uiStatusUpload is UIStatus.Sucesso){

                val urlImagem = uiStatusUpload.dados
                opcional.url = urlImagem

                uiStatus.invoke(UIStatus.Sucesso(""))
            }else {
                uiStatus.invoke(UIStatus.Erro("Erro ao salvar dados"))
            }
        }
    }

}