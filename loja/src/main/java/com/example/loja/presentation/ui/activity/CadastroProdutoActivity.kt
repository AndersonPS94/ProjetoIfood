package com.example.loja.presentation.ui.activity

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.esconderTeclado
import com.example.core.exibirMensagem
import com.example.core.navegarPara
import com.example.loja.databinding.ActivityCadastroProdutoBinding
import com.example.loja.domain.model.Produto
import com.example.loja.domain.model.UploadStorage
import com.example.loja.presentation.viewmodel.ProdutoViewModel
import com.example.loja.util.Constantes
import com.jamiltondamasceno.core.adicionarMascaraMoeda
import com.jamiltondamasceno.core.formatarComoMoeda
import com.jamiltondamasceno.core.moedaToDouble
import com.permissionx.guolindev.PermissionX
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import java.util.UUID

@AndroidEntryPoint
class CadastroProdutoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCadastroProdutoBinding.inflate(layoutInflater)
    }

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }
    
    private val produtoViewModel : ProdutoViewModel by viewModels()
    private var idProduto = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        inicializar()
        solicitarPermissoes()
    }

    override fun onStart() {
        super.onStart()
        recuperarDadosProduto()
    }

    private fun recuperarDadosProduto() {
        if (idProduto.isNotEmpty()){
            produtoViewModel.recuperarProdutoPeloId( idProduto){
                    uiStatus ->
                when(uiStatus) {
                    is UIStatus.Erro -> {
                        alertaCarregamento.fechar()
                        exibirMensagem(uiStatus.erro)
                    }

                    is UIStatus.Sucesso -> {
                        alertaCarregamento.fechar()
                        val produto = uiStatus.dados
                        exibirDadosProduto(produto)
                    }
                    UIStatus.carregando -> {
                        alertaCarregamento.exibir("Recuperando dados do produto")
                    }
                }
            }
        }
    }

    private fun exibirDadosProduto(produto: Produto) {
        if(produto.url.isNotEmpty()){
            Picasso.get()
                .load(produto.url)
                .into(binding.imageCapaProduto)
        }
        if (produto.nome.isNotEmpty()){
            binding.editNomeProduto.setText(produto.nome)
        }
        if (produto.descricao.isNotEmpty()){
            binding.editDescricaoProduto.setText(produto.descricao)
        }
        if (produto.preco > 0.0){
            binding.editPrecoProduto.setText(produto.preco.formatarComoMoeda())
            }


        if (produto.emDestaque == true){
            binding.switchProdutoEmDestaque.isChecked = true
            binding.tlPrecoDestaque.visibility = View.VISIBLE
            if (produto.precoDestaque > 0.0){
                binding.editPrecoDestaqueProduto.setText(produto.precoDestaque.formatarComoMoeda())
            }
        }else {
            binding.switchProdutoEmDestaque.isChecked = false
        }
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
                    "Permissoes necessarias para configurar a loja",
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
        val bundle = intent.extras
        idProduto = bundle?.getString("idProduto") ?: ""

        inicializarEventoClique()
        inicializarMascaraMoeda()
    }

    private fun inicializarMascaraMoeda() {
        binding.editPrecoProduto.adicionarMascaraMoeda(
            local = Locale("pt", "BR"),
            maximoDigitosDecimais = 2,
            simboloMoedaCustomizado = "R$",
            maximoDigitos = 8
        )
        binding.editPrecoDestaqueProduto.adicionarMascaraMoeda(
            local = Locale("pt", "BR"),
            maximoDigitosDecimais = 2,
            simboloMoedaCustomizado = "R$",
            maximoDigitos = 8
        )
    }

    private val selecionarImagemProduto = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri ->
        if (uri != null){
            binding.imageCapaProduto.setImageURI(uri)
            uploadImagemProduto(uri)
        }else {
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    private fun uploadImagemProduto(uri: Uri) {

        produtoViewModel.uploadImagem(
            UploadStorage(
                Constantes.STORAGE_PRODUTOS,
                UUID.randomUUID().toString(),
                uri
            ), idProduto
        ){
                UIStatus ->
            when(UIStatus){
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    exibirMensagem("imagem do produto atualizada com sucesso")
                }
                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(UIStatus.erro)
                }

                is UIStatus.carregando -> {
                    alertaCarregamento.exibir("Fazendo upload da imagem do produto")
                }
            }
        }
    }


    private fun inicializarEventoClique() {
        with(binding) {
            btnCadastroProdutoVoltar.setOnClickListener {
                navegarPara(CardapioActivity::class.java)
            }
            btnSelecionarImagemProduto.setOnClickListener {
                selecionarImagemProduto.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
            switchProdutoEmDestaque.setOnClickListener {
                if (switchProdutoEmDestaque.isChecked){
                    tlPrecoDestaque.visibility = View.VISIBLE
                    editPrecoProduto.setText("")
                }else{
                    tlPrecoDestaque.visibility = View.GONE
                    editPrecoProduto.setText("")
                }
            }
            //cadastrar o produto
            btnSalvarProduto.setOnClickListener {view ->
                view.esconderTeclado()

                //remover focus
                editNomeProduto.clearFocus()
                editDescricaoProduto.clearFocus()
                editPrecoProduto.clearFocus()
                editPrecoDestaqueProduto.clearFocus()

                val nome = editNomeProduto.text.toString()
                val descricao = editDescricaoProduto.text.toString()
                val preco = editPrecoProduto.moedaToDouble()
                val precoDestaque = editPrecoDestaqueProduto.moedaToDouble()
                val emDestaque = switchProdutoEmDestaque.isChecked

                val produto = Produto(
                    id =idProduto, nome = nome, descricao = descricao,
                    preco = preco, precoDestaque = precoDestaque,
                    emDestaque = emDestaque

                )

               val retornoValidacao = validarCampos(produto)
                if (retornoValidacao){
                        cadastrarProduto(produto)
                }else{
                    exibirMensagem("Preencha todos os campos")
                }
            }
        }
    }

    private fun cadastrarProduto(produto: Produto) {
        produtoViewModel.salvar(produto) { uiStatus ->
            when (uiStatus) {
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    idProduto = uiStatus.dados
                    exibirMensagem("Produto atualizado com sucesso")
                }

                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(uiStatus.erro)
                }
                UIStatus.carregando -> {
                    exibirMensagem("Salvando produto")
                }
            }
        }

    }

    private fun validarCampos(produto: Produto) : Boolean {
        if(produto.nome.isEmpty()) return false
        if(produto.descricao.isEmpty()) return false
        if(produto.preco <= 0.0) return false
        if (produto.emDestaque == true) {
            if (produto.precoDestaque <= 0.0) return false
        }

        return true
    }
}