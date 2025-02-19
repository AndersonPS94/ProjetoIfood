package com.example.aulaifood.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aulaifood.data.remote.firebase.repository.autenticacao.IAutenticacaoRepository
import com.example.aulaifood.domain.model.Usuario
import com.example.aulaifood.domain.usecase.AutenticacaoUseCase
import com.example.aulaifood.domain.usecase.ResultadoValidacao
import com.example.core.UIStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AutenticacaoViewModel @Inject constructor(
    private val autenticacaoUseCase: AutenticacaoUseCase,
    private val auteticacaoRepositoryImpl: IAutenticacaoRepository
): ViewModel() {

    private val _resultadoValidacao = MutableLiveData<ResultadoValidacao>()
    val resultadoValidacao : LiveData<ResultadoValidacao>
        get() = _resultadoValidacao

    private val _carregando = MutableLiveData<Boolean>()
    val carregando : LiveData<Boolean>
        get() = _carregando

    fun cadastrarUsuario(usuario: Usuario, uiStatus: (UIStatus<Boolean>) -> Unit) {


        val retornoValidacao = autenticacaoUseCase.validarCadastroUsuario(usuario)
        _resultadoValidacao.value = retornoValidacao
        if (retornoValidacao.sucessoValidacaoCadastro){
            _carregando.value = true
            viewModelScope.launch {
                auteticacaoRepositoryImpl.cadastrarUsuario(
                    usuario, uiStatus
                )
                _carregando.postValue(false)
            }

        }
    }
        fun logarUsuario(usuario: Usuario, uiStatus: (UIStatus<Boolean>)-> Unit ) {
            val retornoValidacao = autenticacaoUseCase.validarloginUsuario(usuario)
            _resultadoValidacao.value = retornoValidacao

            //login usuario
            if(retornoValidacao.sucessoValidacaoLogin){
                _carregando.value = true
                viewModelScope.launch {
                    auteticacaoRepositoryImpl.logarUsuario(usuario, uiStatus)
                    _carregando.postValue(false)

            }
        }
    }

    fun verificarUsuarioLogado(): Boolean{
        return auteticacaoRepositoryImpl.verificarUsuarioLogado()

        }
    }
