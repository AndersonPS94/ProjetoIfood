package com.example.core

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

fun View.esconderTeclado(){
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)

}

fun Activity.exibirMensagem(mensagem: String) {
    Toast.makeText(this, mensagem, Toast.LENGTH_LONG).show()
}

fun <T>Activity.navegarPara(destino: Class<T>, finalizar : Boolean = true) {
        startActivity(
            Intent(applicationContext, destino)
        )
    finish()
}