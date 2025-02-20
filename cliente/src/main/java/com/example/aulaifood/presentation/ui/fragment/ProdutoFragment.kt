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
import com.example.aulaifood.databinding.FragmentProdutoBinding
import com.example.aulaifood.domain.model.Loja
import com.example.aulaifood.domain.model.Produto
import com.example.aulaifood.presentation.ui.adapter.OpicionaisAdapter
import com.example.aulaifood.presentation.viewmodel.ProdutoViewModel
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.exibirMensagem
import com.jamiltondamasceno.core.formatarComoMoeda
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProdutoFragment : Fragment() {

    private lateinit var binding: FragmentProdutoBinding
    private lateinit var opcionaisAdapter: OpicionaisAdapter
    private val produtoFragmentArgs: ProdutoFragmentArgs by navArgs()
    private lateinit var  loja: Loja
    private lateinit var produto: Produto
    private val produtoViewModel : ProdutoViewModel by viewModels()
    private val alertaCarregamento by lazy {
        AlertaCarregamento(requireContext())

    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exibirDadosProduto()
        listarOpcionais()
    }

    private fun listarOpcionais() {
        produtoViewModel.listarOpcionais(
            loja.idLoja,
            produto.id
        ){ uiStatus ->
            when(uiStatus){
                is UIStatus.Erro ->{
                    alertaCarregamento.fechar()
                    activity?.exibirMensagem(uiStatus.erro)
                }
                is UIStatus.Sucesso ->{
                    alertaCarregamento.fechar()
                    val opcionais = uiStatus.dados
                    opcionaisAdapter.adicionarLista(opcionais)
                }
                is UIStatus.carregando ->{
                    alertaCarregamento.exibir("Carregando opcionais")
                }
            }

        }
    }

    private fun exibirDadosProduto() {
        loja = produtoFragmentArgs.loja
        produto = produtoFragmentArgs.produto

        if (produto.url.isNotEmpty()) {
            Picasso.get()
                .load(produto.url)
                .into(binding.imageCapaProduto)
        }
        if (produto.nome.isNotEmpty()) {
            binding.textNomeProdutoDetalhe.text = produto.nome
        }
        if (produto.descricao.isNotEmpty()) {
            binding.textDescriOProdutoDetalhe.text = produto.descricao
        }
        if (produto.emDestaque == true) {
            binding.textPrecoPodutoDetalhe.text = produto.precoDestaque.formatarComoMoeda()
        } else {
            binding.textPrecoPodutoDetalhe.text = produto.preco.formatarComoMoeda()
        }
    }

    private fun inicializarOpcionais() {
        with(binding) {
            opcionaisAdapter = OpicionaisAdapter()
            rvOpcionaisProdutoDetalhe.adapter = opcionaisAdapter
            rvOpcionaisProdutoDetalhe.layoutManager = LinearLayoutManager(
            context, RecyclerView.VERTICAL, false)

        }
    }

    private fun inicializarEventoClique() {
        val navController = findNavController()
        binding.btnProdutoVoltar.setOnClickListener {
            navController.popBackStack()
        }
        binding.btnAdicionarProdutoCarrinho.setOnClickListener {
            navController.navigate(R.id.lojaFragment)
        }
    }

}