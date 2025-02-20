package com.example.aulaifood.data.remote.firebase.repository.autenticacao

import com.example.aulaifood.domain.model.Usuario
import com.example.core.UIStatus
import com.example.loja.util.ConstantesFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AutenticacaoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): IAutenticacaoRepository {

    override suspend fun recuperarDadosUsuarioLogado(
        uiStatus: (UIStatus<Usuario>) -> Unit
    ){
        try {
            val idUsuario = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refUsuario = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_USUARIOS)
                .document(idUsuario)
            val documentSnapshot = refUsuario.get().await()
            if (documentSnapshot.exists()){
                val usuario = documentSnapshot.toObject(Usuario::class.java)
                if (usuario != null){
                    uiStatus.invoke(UIStatus.Sucesso(usuario))
                    }else{
                        uiStatus.invoke(UIStatus.Erro("Usuário não encontrado"))
                    }
                }else{
                    uiStatus.invoke(UIStatus.Erro("Usuário não encontrado"))
                }
            }catch (erroRecuperarDadosUsuario : Exception){
                uiStatus.invoke(
                    UIStatus.Erro("Erro ao recuperar dados do usuario")
                )
        }
    }

    override suspend fun atualizarUsuario(
        usuario: Usuario,
        uiStatus: (UIStatus<String>) -> Unit
    ){
        try {
            val idUsuario = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refUsuario = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_USUARIOS)
                .document(idUsuario)
            refUsuario.update(usuario.mapToUsuarioFirestore()).await()

            uiStatus.invoke(UIStatus.Sucesso(idUsuario))

        }catch (erroAtualizarLoja : Exception){
            uiStatus.invoke(
                UIStatus.Erro("Erro ao atualizar usuario")
            )
        }

    }


    override suspend fun cadastrarUsuario(
        usuario: Usuario,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {

        try {
             firebaseAuth.createUserWithEmailAndPassword(
                usuario.email, usuario.senha
            ).await()

            val idUsuario = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Erro ao cadastrar usuario"))
            //salvar dados do usuario no firestore
            usuario.id = idUsuario
            val usuariosRef = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_USUARIOS)
                .document(idUsuario)

            usuariosRef.set(usuario.mapToUsuarioFirestore()).await()


                uiStatus.invoke(
                    UIStatus.Sucesso(true)
                )

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

    override fun recuperarIdUsuarioLogado(): String {
        return firebaseAuth.currentUser?.uid ?: ""
    }

    override fun deslogarUsuario() {
        firebaseAuth.signOut()
    }
}