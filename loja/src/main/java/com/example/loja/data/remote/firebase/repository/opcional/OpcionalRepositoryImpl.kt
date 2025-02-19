package com.example.loja.data.remote.firebase.repository.opcional

import com.example.core.UIStatus
import com.example.loja.domain.model.Opcional
import com.example.loja.util.ConstantesFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class OpcionalRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
 ): IOpcionalRepository {
    override suspend fun salvar(opcional: Opcional, uiStatus: (UIStatus<String>) -> Unit) {
        try {
                val idLoja = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Usuário não encontrado"))

            val refOpcional = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection(ConstantesFirebase.FIRESTORE_ITENS)
                .document(opcional.idProduto)
                .collection(ConstantesFirebase.FIRESTORE_OPCIONAIS)
                .document()


            val idOpcional = refOpcional.id
            opcional.id = idOpcional
            refOpcional.set(opcional).await()

            uiStatus.invoke(UIStatus.Sucesso(idOpcional))

        }catch (erroSalvarOpcional: Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao salvar opcional"))
        }
    }

    override suspend fun listar(
        idProduto: String,
        uiStatus: (UIStatus<List<Opcional>>) -> Unit) {
        try {
                val idLoja = firebaseAuth.currentUser?.uid ?:
                return uiStatus.invoke(UIStatus.Erro("Usuário não encontrado"))

            val refOpcional = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection(ConstantesFirebase.FIRESTORE_ITENS)
                .document(idProduto)
                .collection(ConstantesFirebase.FIRESTORE_OPCIONAIS)

            val querySnapshot = refOpcional.get().await()
            if (querySnapshot.documents.isNotEmpty()){
                val listaOpcionais = querySnapshot.documents.mapNotNull {
                    documentSnapshot ->
                    documentSnapshot.toObject(Opcional::class.java)
                }
                uiStatus.invoke(UIStatus.Sucesso(listaOpcionais))
            }else {
                uiStatus.invoke(UIStatus.Erro("Nenhum opcional encontrado"))
            }


        }catch (erroListarOpcionais: Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao listar opcionais"))
        }
    }

    override suspend fun remover(opcional: Opcional, uiStatus: (UIStatus<Boolean>) -> Unit) {
        try {
            val idLoja = firebaseAuth.currentUser?.uid ?:
            return uiStatus.invoke(UIStatus.Erro("Usuário não encontrado"))

            val refOpcional = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_PRODUTOS)
                .document(idLoja)
                .collection(ConstantesFirebase.FIRESTORE_ITENS)
                .document(opcional.idProduto)
                .collection(ConstantesFirebase.FIRESTORE_OPCIONAIS)
                .document(opcional.id)

            refOpcional.delete().await()
            uiStatus.invoke(UIStatus.Sucesso(true))
            
        }catch (erroRemoverOpcional: Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao remover opcional"))
        }
    }


}