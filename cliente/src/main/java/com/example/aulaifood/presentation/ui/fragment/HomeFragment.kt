package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentHomeBinding
import com.example.aulaifood.domain.model.FiltroCategoria
import com.example.aulaifood.presentation.ui.adapter.FiltroCategoriaAdapter
import com.example.aulaifood.presentation.ui.adapter.LojasAdapter
import com.example.aulaifood.presentation.viewmodel.LojaViewModel
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.exibirMensagem
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var lojasAdapter: LojasAdapter
    private lateinit var ultimasLojasAdapter: LojasAdapter

    private val listaFiltrosCategoria = listOf(
        FiltroCategoria("Restaurantes", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Restaurantes3_FGY1.png?imwidth=128"),
        FiltroCategoria("Mercados", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Mercados_0kKg.png?imwidth=128"),
        FiltroCategoria("Farmácias", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Farmacia_5xrD.png?imwidth=128"),
        FiltroCategoria("Pet", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Pet_4hAx.png?imwidth=128"),
        FiltroCategoria("Bebidas", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/Bebidas_bkzX.png?imwidth=128"),
        FiltroCategoria("Shopping", "https://static.ifood-static.com.br/image/upload/t_medium/discoveries/floreseperfume_fA1Z.png?imwidth=128"),
        FiltroCategoria("Gourmet", "https://images.tcdn.com.br/img/img_prod/795791/brigadeiro_gourmet_com_chocolate_belga_4_un_caminho_da_fazenda_929_2_304c478e5158e55b8637790dbd629afa.jpg"),
        FiltroCategoria("Cupons","https://static.ifood-static.com.br/image/upload/t_medium/discoveries/03cupons_ESAv.png?imwidth=128")
    )


    private lateinit var alertaCarregamento: AlertaCarregamento
    private val lojaViewModel: LojaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(
            inflater,
            container,
            false
        )
        inicializarFiltros()
        inicializarNotificacoes()
        inicializarFiltrosCategoria()
        inicializarSlider()
        inicializarRecyclerviewLojas()
        inicializarRecyclerviewUltimasLojas()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //carregar dados
        listarLojas()
    }

    private fun listarLojas() {

        alertaCarregamento = AlertaCarregamento(requireContext())
        lojaViewModel.listar { uiStatus ->
            when(uiStatus){
                is UIStatus.Erro ->{
                    alertaCarregamento.fechar()
                    activity?.exibirMensagem(uiStatus.erro)
                }
                is UIStatus.Sucesso ->{
                    alertaCarregamento.fechar()
                    val lojas = uiStatus.dados
                    lojasAdapter.adicionarLista(lojas)
                    ultimasLojasAdapter.adicionarLista(lojas)
                }
                is UIStatus.carregando ->{
                    alertaCarregamento.exibir("Carregando lojas")
                }
            }
        }
    }

    private fun inicializarFiltros() {
        val ordenacaoChecked = binding.chipFiltroOrdenacao.isChecked
        val entregaChecked = binding.chipEntregaGratis.isChecked
        //val retirarChecked = binding.chipParaRetirar.isChecked


        binding.chipFiltroOrdenacao.setOnClickListener {
                val ordenacoes = arrayOf("Ordenação Padrão", "Crescente", "Decrescente")
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Selecione Ordenação")
                    .setItems(ordenacoes) { _, posicao ->
                        val ordenacaoSelecionada = ordenacoes[posicao]
                        if (posicao == 0) {
                            binding.chipFiltroOrdenacao.text = "Ordenação"
                        } else
                            binding.chipFiltroOrdenacao.text = ordenacaoSelecionada
                    }.show()
        }
    }

    private fun inicializarRecyclerviewUltimasLojas() {
        with(binding){
            val orientacao = RecyclerView.HORIZONTAL
            ultimasLojasAdapter = LojasAdapter(orientacao){ loja ->
                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_lojaFragment)
            }
            rvUltimasLojas.adapter = ultimasLojasAdapter
            rvUltimasLojas.layoutManager = LinearLayoutManager(
                context, orientacao, false
            )
        }
    }

    private fun inicializarRecyclerviewLojas() {
        with(binding){
            val orientacao = RecyclerView.VERTICAL
            lojasAdapter = LojasAdapter(orientacao){ loja ->
                /*val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_lojaFragment)*/

                val acao = HomeFragmentDirections.actionHomeFragmentToLojaFragment(loja)
                findNavController().navigate(acao)
            }
            rvLojas.adapter = lojasAdapter
            rvLojas.layoutManager = LinearLayoutManager(
                context, orientacao, false
            )
        }
    }

    private fun inicializarSlider() {
        val tipoEscalaImagem = ScaleTypes.CENTER_INSIDE
        val listaSlides = ArrayList<SlideModel>() // Create image list
        listaSlides.add(SlideModel(
            "https://static.ifood-static.com.br/image/upload/t_high/discoveries/1306PromotionsCampanhasSeloPIJCampanhasAlwaysOncafeprincipal_N5Jv.png?imwidth=1920",

        ))

        listaSlides.add(SlideModel(
            "https://static.ifood-static.com.br/image/upload/t_high/discoveries/1908SMHOUSE12788PromotionsEntregaGratiscapaprincipal_VvdQ.png?imwidth=1920",
        ))

        listaSlides.add(SlideModel(
            "https://static.ifood-static.com.br/image/upload/t_high/discoveries/Promotions09CampanhasListasAONKVMesdoCLienteprincipal_sBYz.png?imwidth=1920",
        ))

        binding.imageSlider.setImageList(listaSlides,tipoEscalaImagem)
        /*binding.imageSlider.setItemClickListener(object : ItemClickListener {
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                Toast.makeText(context,"posicao $position",Toast.LENGTH_SHORT).show()"
            }

        }*/
    }

    private fun inicializarFiltrosCategoria() {
        with(binding){

            val filtroCategoriaAdapter = FiltroCategoriaAdapter()
            rvFiltroCategoria.adapter =filtroCategoriaAdapter
            rvFiltroCategoria.layoutManager = GridLayoutManager(
                context,
                4
            )
            filtroCategoriaAdapter.adicionarItens(listaFiltrosCategoria)
        }
    }

    private fun inicializarNotificacoes() {

        val menuItem = binding.tbHome.menu.findItem(R.id.item_notificacao)
        val textTotalNotificacoes = menuItem
            .actionView?.findViewById<TextView>(R.id.textTotalNotificacoes)

        textTotalNotificacoes?.text = "6"

    }
}