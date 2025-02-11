package com.example.loja.presentation.ui.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.loja.R
import com.example.loja.databinding.ActivityCadastroOpcionaisBinding
import com.example.loja.domain.model.Opcional
import com.example.loja.presentation.ui.adapter.OpicionaisAdapter

class CadastroOpcionaisActivity : AppCompatActivity() {

    private val binding by lazy {  ActivityCadastroOpcionaisBinding.inflate(layoutInflater) }

    private lateinit var opcionaisAdapter: OpicionaisAdapter

    private val opcionais = listOf(
        Opcional("Maionese+", "Adicional de maionsese", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221859_1zkg25hxrlj.jpeg"),
        Opcional("Carne 100% Bovina+", "Adicional de hamburguer 100% carne bovina", "R$ 5,00", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091751_42udtixq9dn.jpeg"),
        Opcional("Alface+", "Adicional de alface", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221855_5sgichmp7ad.jpeg"),
        Opcional("Bacon+", "Adicional de bacon", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221859_kv8snz63as8.jpeg"),
        Opcional("Tomate+", "Adiciional de tomate", "R$ 2,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221858_epn3dgoij64.jpeg"),
        Opcional("Fatia Queijo Cheddar+", "Adicional de queijo cheddar", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221852_73un6kg223m.jpeg"),
        Opcional("Molho Especial+", "Adicionar molho especial", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091753_xmb5zznkqve.jpeg"),
        Opcional("Picles+", "Adicional de picles", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221858_qvcgrckwwk.jpeg"),
        Opcional("Cebola Reidratada+", "Adicional de cebola", "R$ 3,50", "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091748_qqhqyua162m.jpeg"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

            inicializar()
    }

    private fun inicializar() {
            inicializarToolbar()
            inicializarOpcionais()
    }

    private fun inicializarOpcionais() {
        with(binding){
            opcionaisAdapter = OpicionaisAdapter{opcional ->

            }
            opcionaisAdapter.adicionarLista(opcionais)
            rvOpcionais.adapter = opcionaisAdapter
            rvOpcionais.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL, false)
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeTbOpcionais.tbPrincipalLoja
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Adicionar Opcionais"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}