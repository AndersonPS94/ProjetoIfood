package com.example.aulaifood.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.aulaifood.databinding.ItemRvFiltroCategoriaBinding
import com.example.aulaifood.domain.model.FiltroCategoria
import com.squareup.picasso.Picasso

class FiltroCategoriaAdapter : Adapter<FiltroCategoriaAdapter.FiltroCategoriaViewHolder>() {

    class FiltroCategoriaViewHolder(

        private val binding: ItemRvFiltroCategoriaBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(filtroCategoria: FiltroCategoria) {
                binding.textTituloFiltroCategoria.text = filtroCategoria.nome
                if (filtroCategoria.url.isNotEmpty()) {
                    Picasso.get()
                        .load(filtroCategoria.url)
                        .into(binding.imageFiltroCategoria)
                }
        }
    }

    private var listaFiltroCategoria = listOf<FiltroCategoria>()

    fun adicionarItens(listaItens: List<FiltroCategoria>) {
        listaFiltroCategoria = listaItens
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiltroCategoriaViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvFiltroCategoriaBinding.inflate(
            layoutInflater,
            parent,
            false
        )
        return FiltroCategoriaViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listaFiltroCategoria.size
    }

    override fun onBindViewHolder(holder: FiltroCategoriaViewHolder, position: Int) {
        val filtroCategoria = listaFiltroCategoria[position]
        holder.bind(filtroCategoria)
    }
}