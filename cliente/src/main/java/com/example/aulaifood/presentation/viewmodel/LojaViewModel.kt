package com.example.aulaifood.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aulaifood.data.remote.firebase.repository.loja.ILojaRepository
import com.example.aulaifood.domain.model.Loja
import com.example.core.UIStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LojaViewModel @Inject constructor(
    private val lojaRepositoryImpl: ILojaRepository
): ViewModel() {


    fun listar(uiStatus : (UIStatus<List<Loja>>) -> Unit){
        viewModelScope.launch {
            lojaRepositoryImpl.listar(uiStatus)
        }
    }

    fun recuperarDadosLoja(
        idLoja: String,
        uiStatus : (UIStatus<Loja>) -> Unit){
        uiStatus.invoke(UIStatus.carregando)
        viewModelScope.launch {
                lojaRepositoryImpl.recuperarDadosLoja(idLoja, uiStatus)
        }
    }

}