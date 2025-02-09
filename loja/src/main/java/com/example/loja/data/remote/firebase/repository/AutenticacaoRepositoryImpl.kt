package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus
import com.example.loja.domain.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AutenticacaoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): IAutenticacaoRepository {
    override suspend fun cadastrarUsuario(
        usuario: Usuario,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {

        try {
            val retorno = firebaseAuth.createUserWithEmailAndPassword(
                usuario.email, usuario.senha
            ).await() != null

            if (retorno){
                uiStatus.invoke(
                    UIStatus.Sucesso(true)
                )
            }
        }catch (erroUsuarioJaCadastrado : FirebaseAuthUserCollisionException){
            uiStatus.invoke(
                UIStatus.Erro("Usuário já cadastrado!")
            )
        }catch (erroEmailInvalido : FirebaseAuthInvalidCredentialsException){
            uiStatus.invoke(
                UIStatus.Erro("E-mail invalido, digite novamente!")
            )
        }catch (erroSenhaFraca: FirebaseAuthWeakPasswordException){
            uiStatus.invoke(
                UIStatus.Erro("Senha fraca, digite mais caracteres!")
            )
        }catch (erroPadrao: Exception){
            uiStatus.invoke(
                UIStatus.Erro("Erro ao fazer seu cadastro, tente novamente")
            )
        }
    }

    override suspend fun logarUsuario(
        usuario: Usuario,
        uiStatus: (UIStatus <Boolean>) -> Unit
        ) {
        try {
            val retorno = firebaseAuth.signInWithEmailAndPassword(
                usuario.email, usuario.senha
            ).await() != null

            if (retorno){
            uiStatus.invoke(
                UIStatus.Sucesso(true)
                )
            }
        }catch (erroUsuarioInvalido : FirebaseAuthInvalidUserException){
            uiStatus.invoke(
                UIStatus.Erro("E-mail Inválido, Usuario não cadastrado!")
            )
        }catch (erroSenhaInvalida : FirebaseAuthInvalidCredentialsException){
            uiStatus.invoke(
                UIStatus.Erro("Senha invalida!")
            )
        }catch (erroPadrao: Exception){
            uiStatus.invoke(
                UIStatus.Erro("OPS, ocorreu um erro... tente novamente")
            )
        }
    }

    override fun verificarUsuarioLogado(): Boolean {
        return firebaseAuth.currentUser != null
    }
}