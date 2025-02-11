package com.example.loja.presentation.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.loja.databinding.ItemRvOpcionalBinding
import com.example.loja.domain.model.Opcional
import com.squareup.picasso.Picasso

class OpicionaisAdapter(
    private val onClickRemover: (Opcional) -> Unit
) : RecyclerView.Adapter<OpicionaisAdapter.OpcionaisViewHolder>() {

    private var listaOpcionais = listOf<Opcional>()
    fun adicionarLista(lista: List<Opcional>) {
        listaOpcionais = lista
        notifyDataSetChanged()
    }
    inner class OpcionaisViewHolder(
        private val binding: ItemRvOpcionalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(opcional: Opcional) {
            with(binding) {
                textNomeOpcional.text = opcional.nome
                textDescricaoOpcional.text = opcional.descricao
                textPrecoOpcional.text = opcional.preco
                if (opcional.url.isNotEmpty()) {
                    Picasso.get()
                        .load(opcional.url)
                        .into(imageOpcional)
                }
                btnRemoverOpicional.setOnClickListener {
                    onClickRemover(opcional)
                }
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OpcionaisViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRvOpcionalBinding.inflate(layoutInflater, parent, false)
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