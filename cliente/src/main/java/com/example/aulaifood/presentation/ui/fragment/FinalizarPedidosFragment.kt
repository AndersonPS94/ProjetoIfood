package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.databinding.FragmentFinalizarPedidosBinding
import com.example.aulaifood.presentation.ui.adapter.ProdutosAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class finalizarPedidosFragment : Fragment() {

    private lateinit var binding : FragmentFinalizarPedidosBinding
    private lateinit var produtosAdapter: ProdutosAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalizarPedidosBinding.inflate(inflater, container, false)

        inicializarProdutos()

        return binding.root
    }

    private fun inicializarProdutos() {
            with(binding){
                 val orientacao = RecyclerView.VERTICAL
                produtosAdapter = ProdutosAdapter(orientacao){}
                rvProdutosFinalizarPedido.adapter = produtosAdapter
                rvProdutosFinalizarPedido.layoutManager = LinearLayoutManager(context, orientacao, false)
            }
    }
}