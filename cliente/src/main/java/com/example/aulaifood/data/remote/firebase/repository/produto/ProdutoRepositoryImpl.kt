package com.example.aulaifood.data.remote.firebase.repository.produto

import com.example.aulaifood.domain.model.Opcional
import com.example.aulaifood.domain.model.Produto
import com.example.core.UIStatus
import com.example.loja.util.ConstantesFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ProdutoRepositoryImpl @Inject constructor(

    private val firebaseFirestore: FirebaseFirestore
) : IProdutoRepository {

    override suspend fun listarOpcionais(
        idLoja: String,
        idProduto: String,
        uiStatus: (UIStatus<List<Opcional>>) -> Unit
    ) {
        try {
            val refOpcional = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection(ConstantesFirebase.FIRESTORE_ITENS)
                .document(idProduto)
                .collection(ConstantesFirebase.FIRESTORE_OPCIONAIS)
            val querySnapshot = refOpcional.get().await()
            if (querySnapshot.documents.isNotEmpty()){
                val listaOpcionais = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Opcional::class.java)
                }
                uiStatus.invoke(UIStatus.Sucesso(listaOpcionais))
        }else {
            uiStatus.invoke(UIStatus.Sucesso(emptyList()))
        }
            } catch (erroListarOpcionais : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao listar opcionais"))
            }
        }

    override suspend fun listar(
        idLoja: String,
        uiStatus: (UIStatus<List<Produto>>) -> Unit) {
        try {

            val refProdutos = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
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
        idLoja: String,
        idProduto: String,
        uiStatus: (UIStatus<Produto>) -> Unit
    ) {
        try {

            val refProduto = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
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

}