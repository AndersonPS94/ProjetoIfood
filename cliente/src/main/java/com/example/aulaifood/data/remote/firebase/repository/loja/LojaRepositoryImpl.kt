package com.example.aulaifood.data.remote.firebase.repository.loja

import com.example.aulaifood.domain.model.Loja
import com.example.core.UIStatus
import com.example.loja.util.ConstantesFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LojaRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
): ILojaRepository {

    override suspend fun recuperarDadosLoja(
        idLoja: String,
        uiStatus: (UIStatus<Loja>) -> Unit) {
        try {

            val refLoja = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_LOJAS)
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

    override suspend fun listar(
        uiStatus: (UIStatus<List<Loja>>) -> Unit) {
        try {

            val refLojas = firebaseFirestore
                .collection(ConstantesFirebase.FIRESTORE_LOJAS)

            val querySnapshot = refLojas.get().await()

            if(querySnapshot.documents.isNotEmpty()){
                //val loja = documentSnapshot.toObject(Loja::class.java)
                val lojas = querySnapshot.documents.mapNotNull { documentSnapshot ->
                    documentSnapshot.toObject(Loja::class.java)
                }

               uiStatus.invoke(UIStatus.Sucesso(lojas))
            }else{
                uiStatus.invoke(UIStatus.Sucesso(emptyList()))
            }

        }catch (erroRecuperarLojas : Exception){
            uiStatus.invoke(UIStatus.Erro("Erro ao recuperar lojas"))
        }
    }

}