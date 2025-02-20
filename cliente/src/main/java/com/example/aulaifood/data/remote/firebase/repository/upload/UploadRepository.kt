package com.example.aulaifood.data.remote.firebase.repository.upload

import android.net.Uri
import com.example.core.UIStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UploadRepository @Inject constructor(
    private val firebaseStorage: FirebaseStorage,
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun upload(
        local:String, nomeImagem:String, uriImagem: Uri
    ): UIStatus<String>{

        try {
            val idLoja = firebaseAuth.currentUser?.uid?:
            return UIStatus.Erro("Usuario nao logado")

            val lojasRef = firebaseStorage
                .getReference(local)
                .child(idLoja)
                .child(nomeImagem)

            lojasRef.putFile(uriImagem).await()

            val urlImagem = lojasRef.downloadUrl.await().toString()
            return UIStatus.Sucesso(urlImagem)

        } catch (erroUploadImagem:Exception) {
            return UIStatus.Erro("Erro ao fazer upload da imagem")
        }
    }
}