package br.com.smartpos.asky.data.model

data class SCList(
    val name: String? = null,
    val items: List<ProdutoItens> = emptyList(),
)
