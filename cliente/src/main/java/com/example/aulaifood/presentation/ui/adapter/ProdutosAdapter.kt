package com.example.aulaifood.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.aulaifood.databinding.ItemRvDestaquesLojaBinding
import com.example.aulaifood.databinding.ItemRvProdutosLojaBinding
import com.example.aulaifood.domain.model.Produto
import com.jamiltondamasceno.core.formatarComoMoeda
import com.squareup.picasso.Picasso

class ProdutosAdapter(
    private val orientacao: Int,
    private val onClick: (Produto) -> Unit
): Adapter<RecyclerView.ViewHolder>() {

    private var produtos = listOf<Produto>()
    fun adicionarLista(lista: List<Produto>) {
            produtos = lista
        notifyDataSetChanged()
    }

    inner class ProdutosDestaqueViewHolder(
        private val binding: ItemRvDestaquesLojaBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(produto: Produto) {
            with(binding){
            textNomeProdutoDestaqueLoja.text = produto.nome
            textPrecoDestaqueLoja.text = produto.preco.formatarComoMoeda()
            textDescontoDestaqueLoja.text = produto.precoDestaque.formatarComoMoeda()
                if (produto.url.isNotEmpty()) {
                    Picasso.get()
                        .load(produto.url)
                        .into(imageDestaqueLoja)
                }
                clProdutoDestaqueLoja.setOnClickListener {
                    onClick(produto)
                }
            }
        }
    }

    inner class ProdutosViewHolder(
        private val binding: ItemRvProdutosLojaBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(produto: Produto) {
            with(binding){
                textNomeProdutoLoja.text = produto.nome
                textDescricaoProdutoLoja.text = produto.descricao
                textPrecoProdutoLoja.text = produto.preco.formatarComoMoeda()
                if (produto.url.isNotEmpty()) {
                    Picasso.get()
                        .load(produto.url)
                        .into(imageProdutoLoja)
                }
                clProdutoLoja.setOnClickListener {
                    onClick(produto)

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        if (orientacao == RecyclerView.VERTICAL) {
            val itemRvProdutos = ItemRvProdutosLojaBinding.inflate(
                layoutInflater, parent, false
            )
            return ProdutosViewHolder(itemRvProdutos)
        }
        val itemRvProdutosDestaque = ItemRvDestaquesLojaBinding.inflate(
            layoutInflater, parent, false
        )
        return ProdutosDestaqueViewHolder(itemRvProdutosDestaque)

    }

    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val produto = produtos[position]
        when (holder) {
            is ProdutosViewHolder -> holder.bind(produto)
            is ProdutosDestaqueViewHolder -> holder.bind(produto)
        }
    }

}