package com.example.loja.presentation.ui.activity

import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.exibirMensagem
import com.example.core.navegarPara
import com.example.loja.databinding.ActivityLojaBinding
import com.example.loja.domain.model.Loja
import com.example.loja.domain.model.UploadLoja
import com.example.loja.presentation.viewmodel.LojaViewModel
import com.example.loja.util.Constantes
import com.permissionx.guolindev.PermissionX
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LojaActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLojaBinding.inflate(layoutInflater)
    }

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    private val lojaViewModel : LojaViewModel by viewModels()

    private lateinit var spinnerAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
            inicializar()
    }

    override fun onStart() {
        super.onStart()
        carregarDados()
    }

    private fun carregarDados() {
        lojaViewModel.recuperarDadosLoja{ uiStatus ->
            when(uiStatus){
                is UIStatus.Erro-> {
                    alertaCarregamento.fechar()
                    exibirMensagem(uiStatus.erro)
                }
                UIStatus.carregando -> {
                    alertaCarregamento.fechar()
                    exibirMensagem("Carregando dados da loja")
                }

                is UIStatus.Sucesso ->{
                    alertaCarregamento.fechar()
                    val loja = uiStatus.dados
                    val idCategoria = loja.idCategoria
                    exibirDadosLoja(loja)
                    carregarCategorias(idCategoria)
                }

            }

        }
    }

    private fun carregarCategorias(idCategoria: String) {
            lojaViewModel.recuperarCategorias { uiStatus ->
                when (uiStatus) {
                    is UIStatus.Erro -> {
                        exibirMensagem(uiStatus.erro)
                    }
                    is UIStatus.Sucesso -> {

                    }
                    UIStatus.carregando -> {
                        spinnerAdapter.clear()
                        spinnerAdapter.addAll(mutableListOf("Carregando..."))
                    }
                }
            }
    }

    private fun exibirDadosLoja(loja: Loja) {
        if (loja.urlCapa.isNotEmpty()){
            Picasso.get()
                .load(loja.urlCapa)
                .into(binding.imageCapaLoja)
        }
        if (loja.urlPerfil.isNotEmpty()){
            Picasso.get()
                .load(loja.urlPerfil)
                .into(binding.imagePerfilLoja)
        }
        if (loja.nome.isNotEmpty()){
            binding.editNomeLoja.setText(loja.nome)
        }
        if (loja.razaoSocial.isNotEmpty()){
            binding.editRazaoSocialLoja.setText(loja.razaoSocial)
        }
        if (loja.cnpj.isNotEmpty()){
            binding.editCnpjLoja.setText(loja.cnpj)
        }
        if (loja.sobreEmpresa.isNotEmpty()) {
            binding.editSobreLoja.setText(loja.sobreEmpresa)
        }
        if (loja.telefone.isNotEmpty()){
            binding.editTelefoneLoja.setText(loja.telefone)
        }

    }

    private fun inicializar() {
        inicializarSpinnerCategorias()
        inicializarEventosClique()
        inicializarObservaveis()
        solicitarPermissoes()
    }

    private fun inicializarSpinnerCategorias() {

        spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            mutableListOf()
            )

        binding.spinnerCategoriaLoja.adapter
    }

    private val selecionarImagemCapa = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri ->
        if (uri != null){
            binding.imageCapaLoja.setImageURI(uri)
            uploadImagemCapa(uri)
        }else {
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    private val selecionarImagemPerfil = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ){uri ->
        if (uri != null){
            binding.imagePerfilLoja.setImageURI(uri)
            uploadImagemPerfil(uri)
        }else {
            exibirMensagem("Nenhuma imagem selecionada")
        }
    }

    private fun uploadImagemCapa(uri: Uri) {

        lojaViewModel.uploadImagem(
            UploadLoja(
                Constantes.STORAGE_LOJAS,
                "imagem_capa",
                uri
            )
        ){
                UIStatus ->
            when(UIStatus){
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    exibirMensagem("imagem de capa atualizada com sucesso")
                }
                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(UIStatus.erro)
                }

                is UIStatus.carregando -> {
                    alertaCarregamento.exibir("Fazendo upload da imagem de capa")

                }
            }
        }
    }

    private fun uploadImagemPerfil(uri: Uri) {

        lojaViewModel.uploadImagem(
            UploadLoja(
                Constantes.STORAGE_LOJAS,
                "imagem_perfil",
                uri
            )
        ){
            UIStatus ->
            when(UIStatus){
                is UIStatus.Sucesso -> {
                    alertaCarregamento.fechar()
                    exibirMensagem("imagem de perfil atualizada com sucesso")
                }
                is UIStatus.Erro -> {
                    alertaCarregamento.fechar()
                    exibirMensagem(UIStatus.erro)
                }

                is UIStatus.carregando -> {
                    alertaCarregamento.exibir("Fazendo upload da imagem de perfil")

                }
            }
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

    private fun inicializarEventosClique() {
        with(binding) {
            btnLojaVoltar.setOnClickListener {
                navegarPara(HomeActivity::class.java)
            }
            btnSelecionarImagemCapa.setOnClickListener {
                selecionarImagemCapa.launch(PickVisualMediaRequest(ActivityResultContracts
                    .PickVisualMedia.ImageOnly))
            }
            btnSelecionarImgPerfil.setOnClickListener {
                selecionarImagemPerfil.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            }
            btnAtualizar.setOnClickListener {}
        }
    }

    private fun inicializarObservaveis() {

    }
}