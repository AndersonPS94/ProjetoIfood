package com.example.loja.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.esconderTeclado
import com.example.core.exibirMensagem
import com.example.core.navegarPara
import com.example.loja.R
import com.example.loja.databinding.ActivityLoginBinding
import com.example.loja.domain.model.Usuario
import com.example.loja.presentation.viewmodel.AutenticacaoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val alertaCarregamento by lazy {
        AlertaCarregamento(this)
    }

    private val autenticacaoViewModel: AutenticacaoViewModel by viewModels()

    override fun onStart() {
        super.onStart()
        val usuarioLogado = autenticacaoViewModel.verificarUsuarioLogado()
        if (usuarioLogado) {
            navegarPara(HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        inicializar()

    }

    private fun inicializar() {
        inicializarEventoClique()
        inicializarObservaveis()
    }

    private fun inicializarObservaveis() {

        autenticacaoViewModel.carregando.observe(this){carregando ->
            if (carregando) {
                alertaCarregamento.exibir("Efetuando Login!")
            }else {
                alertaCarregamento.fechar()
            }
        }

        autenticacaoViewModel.resultadoValidacao
            .observe(this) { resultadoValidacao ->
                with(binding) {


                    editLoginEmail.error =
                        if (resultadoValidacao.email) null else getString(R.string.erro_cadastro_email)

                    editLoginSenha.error =
                        if (resultadoValidacao.senha) null else getString(R.string.erro_cadastro_senha)

                }
            }
    }

    private fun inicializarEventoClique() {
        with(binding) {
            textCadastro.setOnClickListener {
                startActivity(
                    Intent(applicationContext, CadastroActivity::class.java)
                )
            }
            btnLogin.setOnClickListener {view ->
                view.esconderTeclado()

                //remover focus do teclado
                editLoginEmail.clearFocus()
                editLoginSenha.clearFocus()

                val email = editLoginEmail.text.toString()
                val senha = editLoginSenha.text.toString()
                val usuario = Usuario(email, senha)
                autenticacaoViewModel.logarUsuario(usuario) { uiStatus ->
                    when(uiStatus){
                        is UIStatus.Sucesso -> {
                            navegarPara(HomeActivity::class.java)
                        }
                        is UIStatus.Erro -> {
                            exibirMensagem(uiStatus.erro)
                        }
                    }
                }
            }
        }
    }
}