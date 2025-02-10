package com.example.loja.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.loja.databinding.ItemRvProdutosBinding
import com.example.loja.domain.model.Produto
import com.squareup.picasso.Picasso

class ProdutosAdapter(
    private val onClickOpcional: (Produto) -> Unit,
    private val onClickEditar: (Produto) -> Unit,
    private val onClickRemover: (Produto) -> Unit,
): Adapter<ProdutosAdapter.ProdutosViewHolder>() {

    private var produtos = listOf<Produto>()
    fun adicionarLista(lista: List<Produto>) {
            produtos = lista
        notifyDataSetChanged()
    }


    inner class ProdutosViewHolder(
        private val binding: ItemRvProdutosBinding
    ): RecyclerView.ViewHolder(binding.root){
        fun bind(produto: Produto) {
            with(binding){
                textNomeProdutoLoja.text = produto.nome

                textPrecoProdutoLoja.text = produto.preco
                if (produto.url.isNotEmpty()) {
                    Picasso.get()
                        .load(produto.url)
                        .into(imageProdutoLoja)
                }

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutosViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemRvProdutosBinding.inflate(
                layoutInflater, parent, false
            )
            return ProdutosViewHolder(binding)
        }


    override fun getItemCount(): Int {
        return produtos.size
    }

    override fun onBindViewHolder(holder: ProdutosViewHolder, position: Int) {
        val produto = produtos[position]
        holder.bind(produto)
    }

}