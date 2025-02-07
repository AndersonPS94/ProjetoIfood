package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentPedidosBinding
import com.example.aulaifood.domain.model.PedidoHistorico
import com.example.aulaifood.presentation.ui.adapter.PedidosHistoricoAdapter

class PedidosFragment : Fragment() {

    private lateinit var  binding : FragmentPedidosBinding
    private val pedidosHistorio = listOf(
        PedidoHistorico("30/01/2025",
            "Pizzaria Big Mike",
            "https://static.ifood-static.com.br/image/upload/t_thumbnail/logosgde/0e11bca1-cdcd-42bc-a531-bd2470ff3803/202002010055_iuUW_i.jpg",
            listOf(" Pizza Galo Bronks",  " Pizza Meio à Meio Doce",)),

        PedidoHistorico("20/01/2025",
            "Big Lanche",
            "https://static.ifood-static.com.br/image/upload/t_thumbnail/logosgde/d22c8685-a7e4-43b3-b19a-fb380920fc6d/202408292025_JRAK_i.jpg",
            listOf("Coca-Cola Original 350m",  " Meia Porção de Calabresa",)),

        PedidoHistorico("12/01/2025",
            "Mister Batata",
            "https://static.ifood-static.com.br/image/upload/t_thumbnail/logosgde/dcd4c501-0d01-4871-941e-a6b50f75457f/202407241306_elLC_.jpeg",
            listOf(" Frango cremoso com cheddar", " Strogonoff de Carne com Queijo",)),

        PedidoHistorico("10/01/2025",
            "Restaurante Entre Irmãs",
            "https://static.ifood-static.com.br/image/upload/t_thumbnail/logosgde/26e46208-8d46-4738-8672-2e37e16dbcdc/202410181435_ixrJ_i.jpg",
            listOf("Coca- Cola 2 Litros",  " Feijoada",)),
    )

    private lateinit var pedidosHistoricoAdapter: PedidosHistoricoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPedidosBinding.inflate(
            inflater,
            container,
            false
        )


        inicializarPedidosHistorico()

        return binding.root
    }

    private fun inicializarPedidosHistorico() {
            with(binding) {
                pedidosHistoricoAdapter = PedidosHistoricoAdapter { pedidoHistorico ->}
                pedidosHistoricoAdapter.adicionarLista(pedidosHistorio)
                rvPedidosHistorico.adapter = pedidosHistoricoAdapter
                rvPedidosHistorico.layoutManager = LinearLayoutManager(
                    context,
                    RecyclerView.VERTICAL,
                    false)


            }
    }

}