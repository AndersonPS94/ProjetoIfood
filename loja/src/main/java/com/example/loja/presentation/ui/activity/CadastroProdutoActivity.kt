package com.example.loja.presentation.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.navegarPara
import com.example.loja.R
import com.example.loja.databinding.ActivityCadastroProdutoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CadastroProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroProdutoBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        inicializar()
    }

    private fun inicializar() {
    with(binding) {
        btnCadastroProdutoVoltar.setOnClickListener {
            navegarPara(CardapioActivity::class.java)
            }
        }
    }
}