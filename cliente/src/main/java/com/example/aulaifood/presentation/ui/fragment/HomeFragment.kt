package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentHomeBinding
import com.example.aulaifood.domain.model.FiltroCategoria
import com.example.aulaifood.domain.model.Loja
import com.example.aulaifood.presentation.ui.adapter.FiltroCategoriaAdapter
import com.example.aulaifood.presentation.ui.adapter.LojasAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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

    private val lojas = listOf(
        Loja("Mc Donald's ","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/9ba12409-83c1-4f10-bff8-c6d2f0ff2a36/202408251256_10ZR.png?imwidth=128"),
        Loja("Sushinovo","Japonesa","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/056edd54-d369-45dd-8d46-8d953610b756/202408082004_IKaJ_i.jpg?imwidth=128"),
        Loja("Pizza Big Mike","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/0e11bca1-cdcd-42bc-a531-bd2470ff3803/202002010055_iuUW_i.jpg?imwidth=128"),
        Loja("Pizza Mais","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/4c9906e8-d468-4999-8fb6-e397c8f5b277/202001141045_GDPl_i.jpg?imwidth=128"),
        Loja("Los Frangos","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/0415699b-a920-4b98-943b-5020edbebbc5/202101250116_l4xE_i.png?imwidth=128"),
        Loja("LANCHES DO CEARÁ","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/f0bfbaa9-1f49-4238-9c3c-cb818d65e01c/202406031121_nY0P_i.jpg?imwidth=128"),
        Loja("Dogão Maluco","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/079cdf5c-bca5-49a4-b4f1-6d851ad68c6e/202411252202_kLAh_i.jpg?imwidth=128"),
        Loja("Rei do Açaí ","Açai","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/8d1711c5-22d0-4142-bcf5-dca3419d0310/202408271157_9Lgb_i.jpg?imwidth=128"),
        Loja("Pizza La Páprika","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/a5a3d1d9-658c-4c47-87d8-4c95d18f5edb/202406281602_WCXU_i.jpg?imwidth=128"),
        Loja("Come Come Pizzaria","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/de1a7dcf-aab9-4a23-aaec-5f6d471b59a7/202501101657_GHJo_i.jpg?imwidth=128"),
        Loja("La Casa da Pizza","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/6329d1a2-f10b-48ab-9f44-a512646a2a8b/202404172009_QHep_i.jpg?imwidth=128"),
        Loja("Grupo Batistella","Carnes","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/da67874c-bf06-4d65-8f4b-8abd1a797b40/202204201755_PgzU_i.jpg?imwidth=128"),
        Loja("Big Lanche","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/d22c8685-a7e4-43b3-b19a-fb380920fc6d/202408292025_JRAK_i.jpg?imwidth=128"),
        Loja("Salt N'pizza","Pizza","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/24693801-786d-4961-af17-fc5a0640c90e/202207081214_vdW1_i.jpg?imwidth=128"),
        Loja("Galito Frito","Lanches","https://static.ifood-static.com.br/image/upload/t_medium/logosgde/62a545e4-332f-4824-9f45-1e7976559255/202212262050_SVRN_i.jpg?imwidth=128")
    )

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
        inicializarLojas()
        inicializarUltimasLojas()


        return binding.root
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

    private fun inicializarUltimasLojas() {
        with(binding){
            val orientacao = RecyclerView.HORIZONTAL
            ultimasLojasAdapter = LojasAdapter(orientacao){ loja ->
                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_lojaFragment)
            }
            ultimasLojasAdapter.adicionarLista(lojas)
            rvUltimasLojas.adapter = ultimasLojasAdapter
            rvUltimasLojas.layoutManager = LinearLayoutManager(
                context, orientacao, false
            )
        }
    }

    private fun inicializarLojas() {
        with(binding){
            val orientacao = RecyclerView.VERTICAL
            lojasAdapter = LojasAdapter(orientacao){ loja ->
                val navController = findNavController()
                navController.navigate(R.id.action_homeFragment_to_lojaFragment)
            }
            lojasAdapter.adicionarLista(lojas)
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