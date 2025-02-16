package com.example.core

import android.app.AlertDialog
import android.content.Context
import android.view.View
import android.view.ViewGroup

class AlertaCarregamento(
    private val context: Context
) {

    private var viewCarregamento : View? = null
    private var alertDialog: AlertDialog? = null
    fun exibir(titulo: String) {

        viewCarregamento = View.inflate(context, R.layout.layout_carregamento, null)

        val alertBuilder = AlertDialog.Builder(context)
            .setTitle(titulo)
            .setView(viewCarregamento)
            .setCancelable(false)

        alertDialog = alertBuilder.create()
        alertDialog?.show()
    }

    fun fechar(){
        alertDialog?.dismiss()
    }
}