package com.example.loja.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.UIStatus
import com.example.loja.data.remote.firebase.repository.ILojaRepository
import com.example.loja.data.remote.firebase.repository.UploadRepository
import com.example.loja.domain.model.Categoria
import com.example.loja.domain.model.Loja
import com.example.loja.domain.model.UploadStorage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LojaViewModel @Inject constructor(
    private val uploadRepository: UploadRepository,
    private val lojaRepositoryImpl: ILojaRepository
): ViewModel() {

    fun atualizarLoja(loja: Loja, uiStatus: (UIStatus<Boolean>) -> Unit){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
            lojaRepositoryImpl.atualizarLoja(loja, uiStatus)
        }
    }

    fun recuperarCategorias(uiStatus : (UIStatus<List<Categoria>>) -> Unit){
        viewModelScope.launch {
            lojaRepositoryImpl.recuperarCategorias(uiStatus)
        }
    }

    fun recuperarDadosLoja(uiStatus : (UIStatus<Loja>) -> Unit){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
                lojaRepositoryImpl.recuperarDadosLoja(uiStatus)
        }
    }

    fun uploadImagem(
        uploadStorage: UploadStorage,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {
        uiStatus.invoke(UIStatus.carregando)
            viewModelScope.launch {
                val upload = async {
                    uploadRepository.upload(
                        uploadStorage.nomeImagem, uploadStorage.nomeImagem, uploadStorage.uriImagem
                    )
                }
                val uiStatusUpload = upload.await()
                if (uiStatusUpload is UIStatus.Sucesso) {
                    val urlImagem = uiStatusUpload.dados
                    val campo: Map<String, Any>
                    if (uploadStorage.nomeImagem == "imagem_perfil") {
                            campo = mapOf("urlPerfil" to urlImagem)
                    }else {
                        campo = mapOf("urlCapa" to urlImagem)
                    }
                    lojaRepositoryImpl.atualizarCampo(campo, uiStatus)
                    uiStatus.invoke(UIStatus.Sucesso(true))
                }else {
                    uiStatus.invoke(UIStatus.Erro("Erro ao fazer upload da imagem"))
                }
       }
    }
}