package com.example.aulaifood.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.aulaifood.databinding.ItemRvPedidosHistoricoBinding
import com.example.aulaifood.domain.model.PedidoHistorico
import com.squareup.picasso.Picasso

class PedidosHistoricoAdapter(
    val onClick: (PedidoHistorico) -> Unit
) : Adapter<PedidosHistoricoAdapter.PedidosHistoricoViewHolder>() {

    private var listaPedidos = listOf<PedidoHistorico>()
    fun adicionarLista(lista: List<PedidoHistorico>) {
        listaPedidos = lista
        notifyDataSetChanged()
    }

    inner class PedidosHistoricoViewHolder(
        private val binding: ItemRvPedidosHistoricoBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pedidoHistorico: PedidoHistorico) {
            binding.textDataPedidoHistorico.text = pedidoHistorico.data
            binding.textNomeLojaPedidoHistorico.text = pedidoHistorico.nomeLoja
            if (pedidoHistorico.urlLoja.isNotEmpty()) {
                Picasso.get()
                    .load(pedidoHistorico.urlLoja)
                    .into(binding.imageLojaPedidoHistorico)
            }
            var produtosPedido = ""
            pedidoHistorico.itens.forEachIndexed { indice, nomeProduto ->
                produtosPedido += "$indice - $nomeProduto \n"
            }
            binding.textProdutosPedidoHistorico.text = produtosPedido
            binding.btnConfirmarPedidoHistorico.setOnClickListener {
                onClick(pedidoHistorico)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidosHistoricoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvPedidosHistoricoBinding.inflate(layoutInflater, parent, false)
        return PedidosHistoricoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaPedidos.size
    }

    override fun onBindViewHolder(holder: PedidosHistoricoViewHolder, position: Int) {
        val pedido = listaPedidos[position]
        holder.bind(pedido)
    }
}