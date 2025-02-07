package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.databinding.FragmentFinalizarPedidosBinding
import com.example.aulaifood.domain.model.Produto
import com.example.aulaifood.presentation.ui.adapter.ProdutosAdapter


class finalizarPedidosFragment : Fragment() {

    private lateinit var binding : FragmentFinalizarPedidosBinding
    private lateinit var produtosAdapter: ProdutosAdapter

    private val produtos = listOf(
        Produto("Big Mac Bacon","Dois hambúrgueres, alface, queijo cheddar, molho especial, cebola, picles, bacon e pão com gergelim","R$ 34,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_luodir4k5rm.png"),
        Produto("McFritas Cheddar Bacon","A batata frita mais famosa do mundo, agora com molho com queijo tipo cheddar e bacon crispy. Não dá para resistir, experimente!","R$ 19,00","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010659_dxithxux2f.png"),
        Produto("Molho Ranch","Escolha seu molho favorito para se deliciar com os chicken McNuggets, ou se preferir, com as deliciosas e crocantes McFritas","R$ 2,50","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202408060538_bjjjwl6ixkg.png"),
        Produto("Coca-Cola 500ml","Bebida gelada na medida certa para matar sua sede. Refrescante Coca-Cola 500ml","R$ 15,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010651_y07zn28ecar.png"),
        Produto("McFlurry Kitkat Triple Chocolate","Sobremesa composta por bebida láctea sabor chocolate, cobertura sabor chocolate e tablete de Kitkat Triple Chocolate","R$ 22,50","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_mh1h6b5hpvs.png"),
        )

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
                produtosAdapter.adicionarLista(produtos)
                rvProdutosFinalizarPedido.adapter = produtosAdapter
                rvProdutosFinalizarPedido.layoutManager = LinearLayoutManager(context, orientacao, false)
            }
    }
}