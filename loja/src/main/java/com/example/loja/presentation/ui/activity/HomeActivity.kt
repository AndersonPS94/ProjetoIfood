package com.example.loja.presentation.ui.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import com.example.core.navegarPara
import com.example.loja.R
import com.example.loja.databinding.ActivityHomeBinding
import com.example.loja.presentation.viewmodel.AutenticacaoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val autenticacaoViewModel: AutenticacaoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)
            inicializar()
    }

    private fun inicializar() {
        inicializarToolbar()
        inicializarMenus()
    }

    private fun inicializarMenus() {
        addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_home, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when(menuItem.itemId){
                    R.id.item_gerenciamento_loja -> {
                        navegarPara(LojaActivity::class.java)
                    }

                    R.id.item_cardapio -> {
                        navegarPara(CardapioActivity::class.java, false)
                    }

                    R.id.item_taxa_enredeco -> {}
                    R.id.item_sair -> {
                        autenticacaoViewModel.deslogarUsuario()
                        navegarPara(LoginActivity::class.java)
                    }
                }
                return true
            }

        })
    }


    private fun inicializarToolbar() {
        val toolbar = binding.includeTbHome.tbPrincipalLoja
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setTitle("Gerenciamento de Loja")
        }
    }
}