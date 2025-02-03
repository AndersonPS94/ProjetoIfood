package com.example.aulaifood.presentation.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.aulaifood.R
import com.example.aulaifood.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        inicializar()
    }

    private fun inicializar() {
        inicializarNavegacao()
    }

    private fun inicializarNavegacao() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerViewPrincipal) as NavHostFragment

        NavigationUI.setupWithNavController(
            binding.bottomNavigationPrincipal,
            navHostFragment.navController

        )
    }
}