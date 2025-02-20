package com.example.aulaifood.presentation.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.databinding.ItemRvOpcionaisBinding
import com.example.aulaifood.domain.model.Opcional
import com.jamiltondamasceno.core.formatarComoMoeda
import com.squareup.picasso.Picasso

class OpicionaisAdapter : RecyclerView.Adapter<OpicionaisAdapter.OpcionaisViewHolder>() {

    private var listaOpcionais = listOf<Opcional>()
    fun adicionarLista(lista: List<Opcional>) {
        listaOpcionais = lista
        notifyDataSetChanged()
    }
    class OpcionaisViewHolder(
        private val binding: ItemRvOpcionaisBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bind(opcional: Opcional) {
                with(binding) {
                    textNomeOpcionalDetalhe.text = opcional.nome
                    textDescricaoOpcionalDetalhe.text = opcional.descricao
                    textPrecoOpcionalDetalhe.text = opcional.preco.formatarComoMoeda()
                    if (opcional.url.isNotEmpty()) {
                        Picasso.get()
                            .load(opcional.url)
                            .into(imageOpcionalDetalhe)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpcionaisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvOpcionaisBinding.inflate(layoutInflater, parent, false)
        return OpcionaisViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return listaOpcionais.size
    }

    override fun onBindViewHolder(holder: OpcionaisViewHolder, position: Int) {
        val opcional = listaOpcionais[position]
        holder.bind(opcional)
    }

}