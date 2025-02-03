package com.example.aulaifood.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.aulaifood.R
import com.example.aulaifood.databinding.ActivityLoginBinding
import com.example.aulaifood.domain.model.Usuario
import com.example.aulaifood.presentation.viewmodel.AutenticacaoViewModel
import com.example.core.AlertaCarregamento
import com.example.core.UIStatus
import com.example.core.esconderTeclado
import com.example.core.exibirMensagem
import com.example.core.navegarPara
import com.google.firebase.auth.FirebaseAuth
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

    override fun onCreate(savedInstanceState: Bundle?) {

        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            Thread.sleep(5000)
            val usuarioLogado = autenticacaoViewModel.verificarUsuarioLogado()
            if (usuarioLogado) {
                navegarPara(MainActivity::class.java)
            }
            false
        }

        super.onCreate(savedInstanceState)

        setContentView(binding.root)
        inicializar()
        //FirebaseAuth.getInstance().signOut()
    }
    override fun onStart() {
        super.onStart()
    autenticacaoViewModel.verificarUsuarioLogado()
    }

    private fun inicializar() {
        inicializarEventoClique()
        inicializarObservaveis()
    }

    /*private fun navegarPrincipal(){
        startActivity(
            Intent(applicationContext, MainActivity::class.java)
        )
    }*/
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
                            navegarPara(MainActivity::class.java)
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