package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.data.model.produto.ProdutoResponse
import br.com.smartpos.asky.utils.formatToCurrency

@Composable
fun ButtonAdicionar(
    produtoValor: Double = 0.0,
    qt: Int,
    priceAdd: Double,
    onClick: () -> Unit,
){
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .size(width = 240.dp, height = 50.dp)
            .padding(10.dp, 0.dp, 5.dp, 0.dp),

        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF03A9F4)
        ),
        shape = ShapeDefaults.Small,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = onClick,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 0.dp, 5.dp, 0.dp),
            horizontalArrangement = Arrangement.SpaceBetween,

            ) {

            Text(
                text = "Adicionar",
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        20.dp,
                        12.dp,
                        5.dp,
                        0.dp
                    ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Right,
            )

            Text(
                text = formatToCurrency((produtoValor + priceAdd) * qt),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .padding(
                        5.dp,
                        12.dp,
                        0.dp,
                        0.dp
                    ),
                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
            )

        }

    }
}