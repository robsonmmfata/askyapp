package br.com.smartpos.asky.data.model

data class Mesa (
    val idMesa: Int? = 0,
    val idCliente: Int? = 0,
    val pedido:List<ProdutoItens> = emptyList(),
    val qtConvidado: Int? = 0,
    val status: Boolean? = true,
    val tempo: String? = null,
    val inicio: String? = null,
    val total: Double? = 0.0,
)

