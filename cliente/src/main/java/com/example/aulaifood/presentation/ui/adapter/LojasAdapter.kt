package com.example.aulaifood.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.aulaifood.databinding.ItemRvLojasBinding
import com.example.aulaifood.databinding.ItemRvUltimasLojasBinding
import com.example.aulaifood.domain.model.Loja
import com.squareup.picasso.Picasso

class LojasAdapter(
    private val orientacao: Int,
    private val onClick: (Loja) -> Unit
): RecyclerView.Adapter<ViewHolder>() {

    private var listaLojas = listOf<Loja>()
    fun adicionarLista(lista: List<Loja>) {
        listaLojas = lista
        notifyDataSetChanged()
    }


    inner class LojasViewHolder(
        private val binding: ItemRvLojasBinding
    ) : ViewHolder(binding.root) {
        fun bind(loja: Loja) {
            with(binding) {
                textTituloLoja.text = loja.nome
                textCategoriaLoja.text = loja.categoria
                if (loja.urlPerfil.isNotEmpty()) {
                    Picasso.get()
                        .load(loja.urlPerfil)
                        .into(imageLoja)
                }
                clLoja.setOnClickListener {
                    onClick(loja)
                }
            }
        }
    }

    inner class UltimasLojasViewHolder(
        private val binding: ItemRvUltimasLojasBinding
    ) : ViewHolder(binding.root) {
        fun bind(loja: Loja) {
            with(binding) {
                textTituloUltimaLoja.text = loja.nome
                if (loja.urlPerfil.isNotEmpty()) {
                    Picasso.get()
                        .load(loja.urlPerfil)
                        .into(imageUltimaLoja)
                }
                clUltimaLoja.setOnClickListener {
                    onClick(loja)
                }
            }
        }
    }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val layoutInflater = LayoutInflater.from(parent.context)
            if (orientacao == RecyclerView.VERTICAL) {
                val itemLojasBinding = ItemRvLojasBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
                return LojasViewHolder(itemLojasBinding)
            }
            val itemUltimasLojasBinding = ItemRvUltimasLojasBinding.inflate(
                layoutInflater,
                parent,
                false
            )
            return UltimasLojasViewHolder(itemUltimasLojasBinding)
        }

        override fun getItemCount(): Int {
            return listaLojas.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val loja = listaLojas[position]
            when (holder) {
                is LojasViewHolder -> holder.bind(loja)
                is UltimasLojasViewHolder -> holder.bind(loja)

            }
        }
    }
