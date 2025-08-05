package br.com.smartpos.asky.data.model

import java.math.BigDecimal

data class ProdutoItens(
    val codigo: Int = 0,
    val nomeProduto: String? = null,
    val descricao: String? = "",
    val valor: BigDecimal? = null,
    val qunatidade: Int = 0,
    val categoria: String? = null,
    val subcategoria: String? = null,
    val unidade: String? = null,
    val foto: String? = null,
    val esgotado: Boolean? = null,
)
