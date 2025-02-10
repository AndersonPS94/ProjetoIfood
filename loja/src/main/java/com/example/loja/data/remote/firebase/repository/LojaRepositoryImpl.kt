package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus
import com.example.loja.domain.model.Categoria
import com.example.loja.domain.model.Loja
import com.example.loja.util.Constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LojaRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): ILojaRepository {

    override suspend fun atualizarLoja(
        loja: Loja,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ){
        try {
            val idLoja = firebaseAuth.currentUser?.uid ?:
            return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refLoja = firebaseFirestore
                .collection(Constantes.FIRESTORE_LOJAS)
                .document(idLoja)
            refLoja.update(loja.toMap()).await()

            uiStatus.invoke(UIStatus.Sucesso(true))

        }catch (erroAtualizarLoja : Exception){
            uiStatus.invoke(
                UIStatus.Erro("Erro ao atualizar loja")
            )
        }

    }

    override suspend fun atualizarCampo(
        campo: Map<String, Any>,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {
        try {
            val idLoja = firebaseAuth.currentUser?.uid ?:
            return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refLoja = firebaseFirestore
                .collection(Constantes.FIRESTORE_LOJAS)
                .document(idLoja)

            refLoja.update(campo).await()

            uiStatus.invoke(UIStatus.Sucesso(true))

        }catch (erroAtualizarCampo : Exception){
            uiStatus.invoke(
                UIStatus.Erro("Erro ao atualizar campo")
            )
        }
    }

    override suspend fun recuperarDadosLoja(
        uiStatus: (UIStatus<Loja>) -> Unit) {
        try {

            val idLoja = firebaseAuth.currentUser?.uid ?:
            return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refLoja = firebaseFirestore
                .collection(Constantes.FIRESTORE_LOJAS)
                .document(idLoja)

            val documentSnapshot = refLoja.get().await()
            if(documentSnapshot.exists()){
                    val loja = documentSnapshot.toObject(Loja::class.java)
                if(loja != null){
                    uiStatus.invoke(UIStatus.Sucesso(loja))
                }else {
                    uiStatus.invoke(UIStatus.Erro("Loja não encontrada"))
                }
            }else{
                uiStatus.invoke(UIStatus.Erro("Loja não encontrada"))
            }

        }catch (erroRecuperarDados : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao recuperar dados da loja"))
        }
    }

    override suspend fun recuperarCategorias(
        uiStatus: (UIStatus<List<Categoria>>) -> Unit) {
        try {

            val refCategorias = firebaseFirestore
                .collection(Constantes.FIRESTORE_CATEGORIAS)


            val querySnapshot = refCategorias.get().await()

            if(querySnapshot.documents.isNotEmpty()){
                //val loja = documentSnapshot.toObject(Loja::class.java)
                val listaCategorias = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Categoria::class.java)
                }

               uiStatus.invoke(UIStatus.Sucesso(listaCategorias))
            }else{
                uiStatus.invoke(UIStatus.Sucesso(emptyList()))
            }

        }catch (erroRecuperarCategorias : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao recuperar categorias"))
        }
    }

}