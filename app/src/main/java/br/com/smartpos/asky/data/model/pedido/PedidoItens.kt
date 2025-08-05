package br.com.smartpos.asky.data.model.pedido

import br.com.smartpos.asky.data.model.produto.ExtraLists

data class PedidoItens(
    val idMesa: Int = 0,
    val idProduto: Int = 0,
    val name: String = "",
    val qt: Int = 0,
    val price: Double = 0.0,
    val obs: String = "",
    val extra_lists: ArrayList<Adicionais>
)
