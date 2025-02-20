package com.example.aulaifood.domain.usecase.produto

import com.example.aulaifood.data.remote.firebase.repository.produto.IProdutoRepository
import com.example.aulaifood.domain.model.Produto
import com.example.aulaifood.domain.model.ProdutosSeparados
import com.example.aulaifood.domain.model.TipoProduto
import com.example.core.UIStatus
import javax.inject.Inject

class RecuperarProdutosPorTipoUseCase @Inject constructor(
private val produtoRepositoryImpl: IProdutoRepository
) {
    suspend operator fun invoke(
        idLoja: String,
        uiStatus: (UIStatus<List<ProdutosSeparados>>) -> Unit
    ){

        produtoRepositoryImpl.listar(idLoja){statusRetorno ->
            when(statusRetorno){
                is UIStatus.Sucesso ->{
                    val listaProdutos = statusRetorno.dados
                    val produtoEmDEstaque = listaProdutos
                        .filter { it.emDestaque==true}

                    val produto = listaProdutos
                        .filter { it.emDestaque==false}

                    val produtosSeparados = listOf(
                        ProdutosSeparados(TipoProduto.PRODUTOS_EM_DESTAQUE, produtoEmDEstaque),
                        ProdutosSeparados(TipoProduto.PRODUTOS, produto)
                    )
                    uiStatus.invoke(UIStatus.Sucesso(produtosSeparados))
                }
                is UIStatus.Erro -> uiStatus.invoke(UIStatus.Erro(statusRetorno.erro))

                is UIStatus.carregando -> uiStatus.invoke(UIStatus.carregando)
            }
        }

    }
}