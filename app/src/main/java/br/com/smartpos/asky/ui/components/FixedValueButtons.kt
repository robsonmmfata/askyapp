package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.ui.theme.ColorBlueOne
import br.com.smartpos.asky.utils.formatToCurrency

// Componente de botões de valores fixos
@Composable
fun FixedValueButtons(onValueSelected: (Double) -> Unit, fixedValues: List<Double>) {
    // Função para dividir a lista de valores em grupos de 3
    fun getRows(values: List<Double>, itemsPerRow: Int): List<List<Double>> {
        return values.chunked(itemsPerRow)
    }

    val rows = getRows(fixedValues, 2)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp), // Espaçamento vertical entre linhas
        modifier = Modifier
            .fillMaxWidth() // Preenche a largura total
            .padding(vertical = 8.dp)
    ) {
        items(rows) { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Preenche a largura total
                    .padding(horizontal = 16.dp), // Adiciona um pouco de padding horizontal
                horizontalArrangement = Arrangement.spacedBy(16.dp) // Espaçamento horizontal entre botões
            ) {
                row.forEach { value ->
                    Button(
                        onClick = { onValueSelected(value) }, // Atualiza o valor ao clicar
                        modifier = Modifier
                            .width(150.dp) // Largura dos botões
                            .height(60.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White) // Cor de fundo do botão
                    ) {
                        Text(
                            text = formatToCurrency(value),
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Normal,
                            color = ColorBlueOne, // Cor do texto
                            fontFamily = FontFamily(Font(R.font.ubuntu_bold)) // Fonte personalizada
                        )
                    }
                }
            }
        }
    }
}
