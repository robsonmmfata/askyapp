package br.com.smartpos.asky.ui.components.itens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.utils.formatToCurrency
import java.math.BigDecimal

@Composable
fun ItensPedido(
    pedidos: PedidoItens,
){
    val qt: Int? = pedidos.qt
    val name: String? = pedidos.name
    val valor: Double? = pedidos.price
    val add: String? = pedidos.name

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(10.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            val (
                txtQuantidade,
                textProduto,
                txtInicio,
                txtTempo,
                divider,
                txtPrice
            ) = createRefs()


            Text(
                text = qt.toString(),
                modifier = Modifier.constrainAs(txtQuantidade) {
                    top.linkTo(parent.top) // No topo do ConstraintLayout
                    start.linkTo(parent.start, margin = 20.dp)
                }
            )

            Text(
                text = name!!,
                modifier = Modifier.constrainAs(textProduto) {
                    top.linkTo(parent.top) // Abaixo do Texto 1
                    start.linkTo(txtQuantidade.start, margin = 20.dp) // Alinhado à esquerda
                }
            )

            Text(
                text = "R$ "+ valor!!,
                modifier = Modifier.constrainAs(txtPrice) {
                    top.linkTo(parent.top) // Abaixo do Texto 1
                    end.linkTo(parent.end, margin = 10.dp)
                }
            )

        }
    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 0.dp, 20.dp, 0.dp),
        color = Color.LightGray,
        thickness = 1.dp
    )
}