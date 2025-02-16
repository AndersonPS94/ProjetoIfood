package com.example.loja.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.exibirMensagem
import com.example.core.navegarPara
import com.example.loja.databinding.ActivityCardapioBinding
import com.example.loja.domain.model.Produto
import com.example.loja.presentation.ui.adapter.ProdutosAdapter
import com.example.loja.presentation.viewmodel.ProdutoViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardapioActivity : AppCompatActivity() {

    private val binding by lazy {  ActivityCardapioBinding.inflate(layoutInflater) }

    private lateinit var produtosAdapter: ProdutosAdapter

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    private val produtoViewModel : ProdutoViewModel by viewModels()

    private val produtos = emptyList<Produto>()

    /*private val produtos = listOf(
        Produto("","Big Mac Duplo","Quatro hambúrgueres (100% carne bovina), alface americana, queijo fatiado sabor cheddar, molho especial, cebola, picles e pão com gergelim",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_f2oyog24u6h.png"),
        Produto("","Big Mac Bacon","Dois hambúrgueres, alface, queijo cheddar, molho especial, cebola, picles, bacon e pão com gergelim",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_luodir4k5rm.png"),
        Produto("","Quarterão Com Queijo","Um hambúrguer (100% carne bovina), queijo cheddar, picles, cebola, ketchup, mostarda e pão com gergelim",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_znchkx92ye.png"),
        Produto("","Cheddar McMelt","Um hambúrguer (100% carne bovina), molho lácteo com queijo tipo cheddar, cebola ao molho shoyu e pão escuro com gergelim",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_4nil2qhjsjb.png"),
        Produto("","McChicken","Frango empanado, maionese, alface americana e pão com gergelim",28.90,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_ak2w98680um.png"),
        Produto("","McChicken Bacon","Frango empanado, maionese, bacon, alface americana e pão com gergelim",28.90,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010658_46kdxyz14ye.png"),
        Produto("","Novo Brabo Clubhouse","Dois hambúrgueres de carne 100% bovina, méquinese, a exclusiva maionese especial com sabor de carne defumada",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202408060538_yrozinmr5e.png"),
        Produto("","McFritas Cheddar Bacon","A batata frita mais famosa do mundo, agora com molho com queijo tipo cheddar e bacon crispy. Não dá para resistir, experimente!",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010659_dxithxux2f.png"),
        Produto("","Molho Ranch","Escolha seu molho favorito para se deliciar com os chicken McNuggets, ou se preferir, com as deliciosas e crocantes McFritas",212.50,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202408060538_bjjjwl6ixkg.png"),
        Produto("","McFlurry Kitkat Triple Chocolate","Sobremesa composta por bebida láctea sabor chocolate, cobertura sabor chocolate e tablete de Kitkat Triple Chocolate",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202412030508_mh1h6b5hpvs.png"),
        Produto("","Coca-Cola 500ml","Bebida gelada na medida certa para matar sua sede. Refrescante Coca-Cola 500ml",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010651_y07zn28ecar.png"),
        Produto("","Cappuccino 200ml","Bebida quente composta por leite integral, café e chocolate em pó. 200ml",38.00,28.00,"https://static.ifood-static.com.br/image/upload/t_medium/pratos/a3d94597-96f3-4efe-8aaa-16e04e75bfae/202407010659_8ophrestz2l.png"),
    )*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        inicializar()

    }

    override fun onStart() {
        super.onStart()
        recuperarProdutos()
    }

    private fun recuperarProdutos() {
        produtoViewModel.listar { uiStatus ->
            when(uiStatus) {
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    produtosAdapter.adicionarLista(uiStatus.dados)
                }

                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(uiStatus.erro)
                }
                UIStatus.carregando -> {
                alertaCarregamento.exibir("Recuperando produtos")
                }
            }
        }
    }

    private fun inicializar() {
        inicializarRecyclerViewProdutos()
        inicializarToolbar()
        inicializarEventosClique()
    }

    private fun inicializarEventosClique() {
        with(binding){
            fabAdicionarProduto.setOnClickListener {
                navegarPara(CadastroProdutoActivity::class.java)
            }
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeTbCardapio.tbPrincipalLoja
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = "Cardápio de produtos"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun inicializarRecyclerViewProdutos() {
        with(binding){
            produtosAdapter = ProdutosAdapter(
                onClickOpcional = {produto ->
                    val intent = Intent(applicationContext,CadastroOpcionaisActivity::class.java)
                    intent.putExtra("produto", produto)
                    startActivity(intent)
                },
                onClickEditar = {produto ->
                    val intent = Intent(applicationContext, CadastroProdutoActivity::class.java)
                    intent.putExtra("idProduto", produto.id)
                    startActivity(intent)
                },
                onClickRemover = {produto ->
                    exibirConfirmacaoRemovao(produto)
                },
            )
            produtosAdapter.adicionarLista(produtos)
            rvCardapio.adapter = produtosAdapter
            rvCardapio.layoutManager = LinearLayoutManager(applicationContext,
                RecyclerView.VERTICAL, false)
        }

    }

    private fun exibirConfirmacaoRemovao(produto: Produto) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirmar exclusão do produto?")
            .setMessage("Nome: ${produto.nome} - Preço: ${produto.preco}")
            .setPositiveButton("Confirmar Exclusão"){dialog, _ ->
                removerProduto(produto.id)
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun removerProduto(idProduto: String) {
        produtoViewModel.remover(idProduto) {
            uiStatus ->
            when(uiStatus){
                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                        exibirMensagem(uiStatus.erro)
                }
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    recuperarProdutos()
                    exibirMensagem("Produto removido com sucesso")
                }
                UIStatus.carregando -> {
                    alertaCarregamento.exibir("Removendo produto")
                }

            }
        }
    }
}