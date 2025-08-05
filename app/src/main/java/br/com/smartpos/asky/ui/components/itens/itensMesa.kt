package br.com.smartpos.asky.ui.components.itens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import br.com.smartpos.asky.data.model.Mesa


@Composable
fun ItensMesa(
    mesa: Mesa,
    navController: NavHostController,
){

    val tempoCorrido = mesa.tempo
    val mesaId = mesa.idMesa
    val inicio = mesa.inicio
    val valor = mesa.total

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
                navController.navigate(
                    route = "produto/$mesaId"
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = ShapeDefaults.Medium, // Cantos arredondados
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), // Sombra
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            val (
                txtTempoCorrido,
                textIdMesa,
                txtInicio,
                txtTempo,
                txtPrice
            ) = createRefs()

            Text(
                text = tempoCorrido!!,
                modifier = Modifier.constrainAs(txtTempoCorrido) {
                    top.linkTo(parent.top, margin = 8.dp) // No topo do ConstraintLayout
                    start.linkTo(parent.start)
                    end.linkTo(parent.end) // Centralizado horizontalmente
                }
            )

            Text(
                text = "Mesa "+ mesaId.toString()!!,
                modifier = Modifier.constrainAs(textIdMesa) {
                    top.linkTo(txtTempoCorrido.bottom, margin = 8.dp) // Abaixo do Texto 1
                    start.linkTo(parent.start, margin = 20.dp) // Alinhado à esquerda
                }
            )

            Text(
                text = tempoCorrido!!,
                modifier = Modifier.constrainAs(txtTempo) {
                    top.linkTo(txtTempoCorrido.bottom, margin = 8.dp) // Abaixo do Texto 1
                    end.linkTo(parent.end, margin = 20.dp) // Alinhado à direita

                }
            )

            Text(
                text = inicio!!,
                modifier = Modifier.constrainAs(txtInicio) {
                    top.linkTo(textIdMesa.bottom, margin = 8.dp) // Abaixo do Texto 1
                    start.linkTo(parent.start, margin = 20.dp) // Alinhado à esquerda
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )

            Text(
                text = "R$ "+valor!!,
                modifier = Modifier.constrainAs(txtPrice) {
                    top.linkTo(txtTempo.bottom, margin = 8.dp) // Abaixo do Texto 1
                    end.linkTo(parent.end, margin = 20.dp) // Alinhado à direita
                    bottom.linkTo(parent.bottom, margin = 10.dp)
                }
            )

        }
    }

}
