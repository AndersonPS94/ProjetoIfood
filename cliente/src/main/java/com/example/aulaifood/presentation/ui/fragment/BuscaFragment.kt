package com.example.aulaifood.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaifood.R
import com.example.aulaifood.databinding.FragmentBuscaBinding
import com.example.aulaifood.presentation.ui.adapter.LojasAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BuscaFragment : Fragment() {

    private lateinit var binding : FragmentBuscaBinding
    private lateinit var lojasAdapter: LojasAdapter




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBuscaBinding.inflate(
            inflater,
            container,
            false
        )
        inicializarLojas()
        return binding.root

    }

    private fun inicializarLojas() {
        with(binding){
            val orientacao = RecyclerView.VERTICAL
            lojasAdapter = LojasAdapter(orientacao){ loja ->
                val navController = findNavController()
                navController.navigate(R.id.action_buscaFragment_to_lojaFragment)
            }
            rvBuscaLojas.adapter = lojasAdapter
            rvBuscaLojas.layoutManager = LinearLayoutManager(
                context, orientacao, false
            )
        }
    }
}