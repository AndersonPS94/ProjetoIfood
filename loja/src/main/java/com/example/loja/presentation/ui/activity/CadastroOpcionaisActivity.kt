package com.example.loja.presentation.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.esconderTeclado
import com.example.core.exibirMensagem
import com.example.loja.R
import com.example.loja.databinding.ActivityCadastroOpcionaisBinding
import com.example.loja.domain.model.Opcional
import com.example.loja.domain.model.Produto
import com.example.loja.domain.model.UploadStorage
import com.example.loja.presentation.ui.adapter.OpicionaisAdapter
import com.example.loja.presentation.viewmodel.OpcionalViewModel
import com.example.loja.util.Constantes
import com.jamiltondamasceno.core.adicionarMascaraMoeda
import com.jamiltondamasceno.core.formatarComoMoeda
import com.jamiltondamasceno.core.moedaToDouble
import com.permissionx.guolindev.PermissionX
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID
@AndroidEntryPoint
class CadastroOpcionaisActivity : AppCompatActivity() {

    private val binding by lazy {  ActivityCadastroOpcionaisBinding.inflate(layoutInflater) }

    private lateinit var opcionaisAdapter: OpicionaisAdapter
    private var produto: Produto? = null
    private var uriOpcional: Uri? = null
    private val opcionais = listOf(
        Opcional(
            "",
            "",
            "Maionese+",
            "Adicional de maionsese",
            5.90,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221859_1zkg25hxrlj.jpeg"
        ),

        Opcional("",
            "",
            "Carne 100% Bovina+",
            "Adicional de hamburguer 100% carne bovina",
            5.00,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091751_42udtixq9dn.jpeg"
        ),

        Opcional("",
            "",
            "Alface+",
            "Adicional de alface",
            3.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221855_5sgichmp7ad.jpeg"
        ),

        Opcional("",
            "",
            "Bacon+",
            "Adicional de bacon",
            3.60,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221859_kv8snz63as8.jpeg"
        ),

        Opcional("",
            "",
            "Tomate+",
            "Adiciional de tomate",
            2.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221858_epn3dgoij64.jpeg"
        ),

        Opcional("",
            "",
            "Fatia Queijo Cheddar+",
            "Adicional de queijo cheddar",
            3.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221852_73un6kg223m.jpeg"
        ),

        Opcional("",
            "",
            "Molho Especial+",
            "Adicionar molho especial",
            3.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091753_xmb5zznkqve.jpeg"
        ),

        Opcional("",
            "",
            "Picles+",
            "Adicional de picles",
            3.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/7dc9afad-89c9-4719-9c9b-9210b35447bd/202408221858_qvcgrckwwk.jpeg"
        ),

        Opcional("",
            "",
            "Cebola Reidratada+",
            "Adicional de cebola",
            3.50,
            "https://static.ifood-static.com.br/image/upload/t_medium/pratos/d2fccef3-7bf6-4f04-b2a5-0ce70a3afc97/202409091748_qqhqyua162m.jpeg"
        ),
    )
    private val opcionalViewModel: OpcionalViewModel by viewModels()
    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

            inicializar()
            solicitarPermissoes()
    }

    private fun solicitarPermissoes() {
        val listaPermissoes = mutableListOf<String>()

        //Solicitar permissoes de acordo com a versao
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.UPSIDE_DOWN_CAKE){
            listaPermissoes.add(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
            listaPermissoes.add(android.Manifest.permission.READ_MEDIA_IMAGES)

        }else if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU){
            listaPermissoes.add(android.Manifest.permission.READ_MEDIA_IMAGES)
        }else{
            listaPermissoes.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        PermissionX.init(this)
            .permissions(listaPermissoes)
            .explainReasonBeforeRequest()
            .onExplainRequestReason{scope , permissoesNegadas ->
                scope.showRequestReasonDialog(
                    permissoesNegadas,
                    "Permissoes necessarias para configurar o Opcional",
                    "Permitir",
                    "Negar"
                )
            }
            .onForwardToSettings{scope, permissoesNegadas ->
                scope.showForwardToSettingsDialog(
                    permissoesNegadas,
                    "Você precisa conceder as permissoes necessarias" +
                            " manualmente em configurações",
                    "Abrir configurações",
                    "Cancelar"
                )
            }
            .request{todasConcedidas, permissoesConcedidas, permissoesNegadas ->
                if (!todasConcedidas){
                    exibirMensagem("Permissoes negadas, não é possivel continuar")
                }
            }
    }

    private fun inicializar() {
            inicializarDadosProduto()
            inicializarToolbar()
            inicializarOpcionais()
            inicializarEventosClique()
    }

    private fun inicializarEventosClique() {
        with(binding){
                btnSelecionarImagemOpcional.setOnClickListener {
                    selecionarImagemOpcional.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )

                }

            btnSalvarOpcional.setOnClickListener { view ->
                view.esconderTeclado()
                // Remover Focus
                editNomeOpcional.clearFocus()
                editDescricaoOpcional.clearFocus()
                editPrecoOpcional.clearFocus()

                val nome = editNomeOpcional.text.toString()
                val preco = editPrecoOpcional.moedaToDouble()
                val descricao = editDescricaoOpcional.text.toString()

                produto?.let { produto -> 
                    val opcional = Opcional(
                        idProduto = produto.id, nome = nome,
                        preco = preco, descricao = descricao
                    )
                    val resultadoValidacao = validaDadosOpcional(opcional)
                    if (resultadoValidacao){
                        salvarOpcional(opcional)
                    }else {
                        exibirMensagem("Preencha todos os campos")
                    }
                }
            }
        }
    }

    private fun salvarOpcional(opcional: Opcional) {

        uriOpcional?.let { uri ->
            opcionalViewModel.salvar(
                UploadStorage(Constantes.STORAGE_OPCIONAIS, UUID.randomUUID().toString(), uri), opcional
            ){ uiStatus ->
                when(uiStatus){
                    is UIStatus.Erro ->{
                        alertaCarregamento.fechar()
                        exibirMensagem(uiStatus.erro)
                    }
                    is UIStatus.Sucesso ->{
                        alertaCarregamento.fechar()
                        exibirMensagem("Opcional salvo com sucesso")

                    }
                    UIStatus.carregando -> alertaCarregamento.exibir("salvando dados do opcional")
                }

            }
        }
    }

    private fun validaDadosOpcional(opcional: Opcional): Boolean {
        if (opcional.nome.isEmpty()) return false
        if (opcional.descricao.isEmpty()) return false
        if (opcional.preco == 0.0) return false
        if (uriOpcional == null) return false

        return true
    }


    private val selecionarImagemOpcional = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri ->
        if (uri != null){
            binding.imageCapaOpcional.setImageURI(uri)
            uriOpcional = uri
        }else {
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    private fun inicializarDadosProduto() {
        binding.editPrecoOpcional.adicionarMascaraMoeda()
        val bundle = intent.extras
         produto = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                bundle?.getParcelable("produto", Produto::class.java)
            } else {
                bundle?.getParcelable<Produto>("produto")
            }
                produto?.let {p ->
                    binding.textDadosProduto.text = "${p.nome} - ${p.preco.formatarComoMoeda()}"
                }
    }

    private fun inicializarOpcionais() {
        with(binding){
            opcionaisAdapter = OpicionaisAdapter{opcional ->

            }
            opcionaisAdapter.adicionarLista(opcionais)
            rvOpcionais.adapter = opcionaisAdapter
            rvOpcionais.layoutManager = LinearLayoutManager(applicationContext,RecyclerView.VERTICAL, false)
        }
    }

    private fun inicializarToolbar() {
        val toolbar = binding.includeTbOpcionais.tbPrincipalLoja
        setSupportActionBar(toolbar)
        supportActionBar?.apply {
            title = "Adicionar Opcionais"
            setDisplayHomeAsUpEnabled(true)
        }
    }
}