package com.example.loja.data.remote.firebase.repository

import com.example.core.UIStatus
import com.example.loja.util.Constantes
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class LojaRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
): ILojaRepository {
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

}