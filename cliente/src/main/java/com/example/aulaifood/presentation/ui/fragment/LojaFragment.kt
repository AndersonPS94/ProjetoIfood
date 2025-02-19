package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentLojaBinding
import com.example.aulaifood.domain.model.Loja
import com.example.aulaifood.presentation.ui.adapter.ProdutosAdapter
import com.example.aulaifood.presentation.viewmodel.LojaViewModel
import com.example.aulaifood.presentation.viewmodel.ProdutoViewModel
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.exibirMensagem
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LojaFragment : Fragment() {

    private lateinit var binding: FragmentLojaBinding
    private lateinit var produtosAdapter: ProdutosAdapter
    private lateinit var produtosAdapterDestaque : ProdutosAdapter
    private lateinit var loja: Loja
    private val lojaFragmentArgs: LojaFragmentArgs by navArgs()
    private val alertaCarregamento by lazy {
        AlertaCarregamento(requireContext())
    }
    private val produtoViewModel: ProdutoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentLojaBinding.inflate(
            inflater,
            container,
            false
        )

        inicializarEventosClique()
        inicializarProdutosDestaque()
        inicializarProdutos()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        exibirDadosLoja()
        listarProdutos()
    }

    private fun listarProdutos() {
        produtoViewModel.listar(loja.idLoja) { uiStatus ->
            when(uiStatus){
                is UIStatus.Erro ->{
                    alertaCarregamento.fechar()
                    activity?.exibirMensagem(uiStatus.erro)
                }
                is UIStatus.Sucesso ->{
                    alertaCarregamento.fechar()
                    val produtos = uiStatus.dados
                    produtosAdapter.adicionarLista(produtos)
                    produtosAdapterDestaque.adicionarLista(produtos)
                }
                is UIStatus.carregando ->{
                    alertaCarregamento.exibir("Carregando produtos")
                }
            }
        }
    }

    private fun exibirDadosLoja() {
        loja = lojaFragmentArgs.loja
        if(loja.urlCapa.isNotEmpty()){
            Picasso.get()
                .load(loja.urlCapa)
                .into(binding.imageCapaLoja)
        }

        if(loja.urlPerfil.isNotEmpty()){
            Picasso.get()
                .load(loja.urlPerfil)
                .into(binding.imagePerfilLoja)
        }

        if(loja.nome.isNotEmpty()){
            binding.textNomeLoja.text = loja.nome
        }
        
        if(loja.categoria.isNotEmpty()){
            binding.textCategoriaLoja.text = loja.categoria
        }
    }


    private fun inicializarProdutosDestaque() {
            with(binding){
                val orientacao = RecyclerView.HORIZONTAL
                produtosAdapterDestaque = ProdutosAdapter(orientacao){
                    produto ->
                    val navController = findNavController()
                    navController.navigate(R.id.action_lojaFragment_to_produtoFragment)
                }
                rvDestaqueProdutosLoja.adapter = produtosAdapterDestaque
                rvDestaqueProdutosLoja.layoutManager = LinearLayoutManager(context, orientacao, false)
            }
    }

    private fun inicializarProdutos() {
        with(binding){
            val orientacao = RecyclerView.VERTICAL
            produtosAdapter = ProdutosAdapter(orientacao){
                    produto ->
                val navController = findNavController()
                navController.navigate(R.id.action_lojaFragment_to_produtoFragment)
            }
            rvProdutosLoja.adapter = produtosAdapter
            rvProdutosLoja.layoutManager = LinearLayoutManager(context, orientacao, false)
        }

    }


    private fun inicializarEventosClique() {
        val navController = findNavController()
        binding.btnLojaVoltar.setOnClickListener {
            navController.navigate(R.id.homeFragment)
        }

        binding.btnVerItensFinalizarPedido.setOnClickListener {
                navController.navigate(R.id.action_lojaFragment_to_finalizarPedidosFragment)

        }
    }
}