package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentLojaBinding
import com.example.aulaifood.databinding.ItemRvDestaquesLojaBinding
import com.example.aulaifood.domain.model.Produto
import com.example.aulaifood.presentation.ui.adapter.ProdutosAdapter


class LojaFragment : Fragment() {

    private lateinit var binding: FragmentLojaBinding
    private lateinit var produtosAdapter: ProdutosAdapter
    private lateinit var produtosAdapterDestaque : ProdutosAdapter

    private val produtosDestaque = listOf(
        Produto("MéquiBox - 4 Mcofertas Médias","São 4 Mcofertas para você compartilhar com quem preferir","R$ 136,80","R$ 100,80","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010657_3ys1ahchwqa.png"),
        Produto("Brinquedo Capitão Pikachu","Brinquedos com tema de Pokemon","R$ 20,00","R$ 16,00","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202501210509_yxelou9khrh.png"),
        Produto("MéquiBox - 3 Mcofertas Médias","São 3 Mcofertas para você compartilhar com quem preferir","R$ 103,95","R$ 70,95","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010657_u8ktssdu58i.png"),
        Produto("McOferta Média Duplo Burger Bacon","Dois hambúrgueres (100% carne bovina), queijo cheddar, cebola, fatias de bacon, ketchup, mostarda e pão com gergelim, acompanhamento e bebida","R$ 41,50","R$ 21,50","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010657_jatpsp7qdcb.png"),
        Produto("McOferta Média Duplo Burger com Queijo","Dois hambúrgueres, uma explosão de sabor, acompanhamento e bebida","R$ 42,50","R$ 22,50","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010657_tc1avr9qsab.png"),
        Produto("McOferta Média McNífico Bacon","Um hambúrguer (100% carne bovina), bacon, alface americana, cebola, queijo cheddar, tomate, maionese, ketchup, mostarda e pão com gergelim, acompanhamento e bebida","R$ 53,90","R$ 33,90","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010656_rqefvkxsxi.png"),
        Produto("McOferta Média Triplo Burger","Três hambúrgueres (100% carne bovina), queijo cheddar, cebola, picles, ketchup, mostarda e pão com gergelim, acompanhamento e bebida","R$ 44,90","R$ 24,90","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010656_klpf8t3zi1.png"),
        Produto("McLanche Feliz com Mcfiesta","O McLanche Feliz acompanha sanduíche Mcfiesta com tomatinho ou McFritas como acompanhamento, uma refrescante bebida e Petit Suisse do Méqui","R$ 37,60","R$ 30,60","https://static.ifood-static.com.br/image/upload/t_medium/pratos/673af3f9-173f-4b4e-a7ca-d22cae8e4ef0/202410151021_m8kx6f7l9j.png"),
    )

    private val produtos = listOf(
        Produto("Big Mac Duplo","Quatro hambúrgueres (100% carne bovina), alface americana, queijo fatiado sabor cheddar, molho especial, cebola, picles e pão com gergelim","R$ 38,00","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_f2oyog24u6h.png"),
        Produto("Big Mac Bacon","Dois hambúrgueres, alface, queijo cheddar, molho especial, cebola, picles, bacon e pão com gergelim","R$ 34,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_luodir4k5rm.png"),
        Produto("Quarterão Com Queijo","Um hambúrguer (100% carne bovina), queijo cheddar, picles, cebola, ketchup, mostarda e pão com gergelim","R$ 31,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_znchkx92ye.png"),
        Produto("Cheddar McMelt","Um hambúrguer (100% carne bovina), molho lácteo com queijo tipo cheddar, cebola ao molho shoyu e pão escuro com gergelim","R$ 33,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_4nil2qhjsjb.png"),
        Produto("McChicken","Frango empanado, maionese, alface americana e pão com gergelim","R$ 29,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_ak2w98680um.png"),
        Produto("McChicken Bacon","Frango empanado, maionese, bacon, alface americana e pão com gergelim","R$ 31,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_46kdxyz14ye.png"),
        Produto("Novo Brabo Clubhouse","Dois hambúrgueres de carne 100% bovina, méquinese, a exclusiva maionese especial com sabor de carne defumada","R$ 46,00","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202408060538_yrozinmr5e.png"),
        Produto("McFritas Cheddar Bacon","A batata frita mais famosa do mundo, agora com molho com queijo tipo cheddar e bacon crispy. Não dá para resistir, experimente!","R$ 19,00","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010659_dxithxux2f.png"),
        Produto("Molho Ranch","Escolha seu molho favorito para se deliciar com os chicken McNuggets, ou se preferir, com as deliciosas e crocantes McFritas","R$ 2,50","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202408060538_bjjjwl6ixkg.png"),
        Produto("McFlurry Kitkat Triple Chocolate","Sobremesa composta por bebida láctea sabor chocolate, cobertura sabor chocolate e tablete de Kitkat Triple Chocolate","R$ 22,50","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_mh1h6b5hpvs.png"),
        Produto("Coca-Cola 500ml","Bebida gelada na medida certa para matar sua sede. Refrescante Coca-Cola 500ml","R$ 15,90","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010651_y07zn28ecar.png"),
        Produto("Cappuccino 200ml","Bebida quente composta por leite integral, café e chocolate em pó. 200ml","R$ 7,50","","https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010659_8ophrestz2l.png"),
        )

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


    private fun inicializarProdutosDestaque() {
            with(binding){
                val orientacao = RecyclerView.HORIZONTAL
                produtosAdapterDestaque = ProdutosAdapter(orientacao){
                    produto ->
                    val navController = findNavController()
                    navController.navigate(R.id.action_lojaFragment_to_produtoFragment)
                }
                produtosAdapterDestaque.adicionarLista(produtosDestaque)
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
            produtosAdapter.adicionarLista(produtos)
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