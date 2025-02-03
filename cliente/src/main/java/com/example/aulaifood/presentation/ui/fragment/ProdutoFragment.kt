package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentProdutoBinding


class ProdutoFragment : Fragment() {

    private lateinit var binding: FragmentProdutoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProdutoBinding.inflate(
            inflater,
            container,
            false
        )
        inicializarEventoClique()
        return binding.root

    }

    private fun inicializarEventoClique() {
        binding.btnProdutoVoltar.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.lojaFragment)
        }
    }

}