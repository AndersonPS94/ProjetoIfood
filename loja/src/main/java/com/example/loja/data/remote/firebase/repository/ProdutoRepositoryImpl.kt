package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus
import com.example.loja.domain.model.Produto
import com.example.loja.util.Constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProdutoRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : IProdutoRepository {

    override suspend fun salvar(
        produto: Produto,
        uiStatus: (UIStatus<String>) -> Unit
    ) {
        try {

            val idLoja = firebaseAuth.currentUser?.uid
                ?: return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refProduto = firebaseFirestore
                .collection(Constantes.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection("itens")
                .document()

            val idProduto = refProduto.id
            produto.id = idProduto

            refProduto.set(produto).await()

            uiStatus.invoke(UIStatus.Sucesso(idProduto))
        } catch (erroSalvarProduto: Exception) {
            uiStatus.invoke(UIStatus.Erro("Erro ao salvar produto"))
    }


    }

    override suspend fun atualizar(produto: Produto, uiStatus: (UIStatus<String>) -> Unit) {

        try {

            val idLoja = firebaseAuth.currentUser?.uid
                ?: return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refProduto = firebaseFirestore
                .collection(Constantes.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection("itens")
                .document(produto.id)

            refProduto.update(produto.toMap()).await()

            uiStatus.invoke(UIStatus.Sucesso(produto.id))
        } catch (erroAtualizarProduto: Exception) {
            uiStatus.invoke(UIStatus.Erro("Erro ao atualizar produto"))


        }

    }

    override suspend fun listar(uiStatus: (UIStatus<List<Produto>>) -> Unit) {
        try {
            val idLoja = firebaseAuth.currentUser?.uid
                ?: return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refProdutos = firebaseFirestore
                .collection(Constantes.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection("itens")

            val querySnapshot = refProdutos.get().await()

            if (querySnapshot.documents.isNotEmpty()){
                val listaProdutos = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Produto::class.java)
                }
                uiStatus.invoke(UIStatus.Sucesso(listaProdutos))
            }else {
                uiStatus.invoke(UIStatus.Sucesso(emptyList()))
            }
        }catch (erroListarProdutos : Exception){
                uiStatus.invoke(UIStatus.Erro("Erro ao listar produtos"))
        }
    }

    override suspend fun recuperarProdutoPeloId(
        idProduto: String,
        uiStatus: (UIStatus<Produto>) -> Unit
    ) {
        try {

            val idLoja = firebaseAuth.currentUser?.uid
                ?: return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))

            val refProduto = firebaseFirestore
                .collection(Constantes.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection("itens")
                .document(idProduto)

            val documentSnapshot = refProduto.get().await()
            val produto = documentSnapshot.toObject(Produto::class.java)

            if (produto != null){
                uiStatus.invoke(UIStatus.Sucesso(produto))
            }else {
                uiStatus.invoke(UIStatus.Erro("Produto não encontrado"))
            }

        }catch (erroRecuperarProduto : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao recuperar produto"))
        }
    }

    override suspend fun remover(
        idProduto: String,
        uiStatus: (UIStatus<Boolean>) -> Unit
    ) {
        try {
            val idLoja = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Usuário não logado"))


            val refProduto = firebaseFirestore
                .collection(Constantes.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection("itens")
                .document(idProduto)

            refProduto.delete().await()

            uiStatus.invoke(UIStatus.Sucesso(true))

        }catch (erroRecuperarProduto : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao remover produto"))
        }
    }
}