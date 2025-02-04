package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentProdutoBinding
import com.example.aulaifood.domain.model.Opcional
import com.example.aulaifood.presentation.ui.adapter.OpicionaisAdapter


class ProdutoFragment : Fragment() {

    private lateinit var binding: FragmentProdutoBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProdutoBinding.inflate(
            inflater,
            container,
            false
        )
        inicializarOpcionais()
        inicializarEventoClique()
        return binding.root

    }

    private fun inicializarOpcionais() {
        with(binding) {
            opcionaisAdapter = OpicionaisAdapter()
            opcionaisAdapter.adicionarLista(opcionais)
            rvOpcionaisProdutoDetalhe.adapter = opcionaisAdapter
            rvOpcionaisProdutoDetalhe.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false)

        }
    }

    private fun inicializarEventoClique() {
        val navController = findNavController()
        binding.btnProdutoVoltar.setOnClickListener {
            navController.navigate(R.id.lojaFragment)
        }
        binding.btnAdicionarProdutoCarrinho.setOnClickListener {
            navController.navigate(R.id.lojaFragment)
        }
    }

}