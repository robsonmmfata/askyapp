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
fun ItensAdicionais(
//    mesa: Mesa,
//    navController: NavHostController
){

//    val tempoCorrido = mesa.tempo
//    val mesaId = mesa.idMesa
//    val inicio = mesa.inicio
//    val valor = mesa.total

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable {
//                navController.navigate(
//                    route = "produto/$mesaId"
//                )
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
                text = "tempoCorrido!!",
                modifier = Modifier.constrainAs(txtTempoCorrido) {
                    top.linkTo(parent.top, margin = 8.dp) // No topo do ConstraintLayout
                    start.linkTo(parent.start)
                    end.linkTo(parent.end) // Centralizado horizontalmente
                }
            )

        }
    }

}
