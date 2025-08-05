package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.ui.theme.ColorBlueOne

// Componente do teclado numérico personalizado
@Composable
fun CustomNumericKeyboard(onKeyPressed: (String) -> Unit) {
    val buttonColor = Color.White
    val textColor = ColorBlueOne

    Column {
        val rows = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf(".", "0", "C")
        )

        rows.forEach { row ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), // Espaçamento vertical entre linhas
                horizontalArrangement = Arrangement.spacedBy(8.dp) // Espaçamento horizontal entre botões
            ) {
                row.forEach { key ->
                    if (key == "C") {
                        IconButton(
                            onClick = { onKeyPressed(key) },
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(buttonColor)
                        ) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.ic_delete),
                                contentDescription = "Delete",
                                tint = textColor
                            )
                        }
                    } else {
                        Button(
                            onClick = { onKeyPressed(key) },
                            colors = ButtonDefaults.buttonColors(containerColor = buttonColor),
                            modifier = Modifier
                                .weight(1f)
                                .height(60.dp)
                                .clip(RoundedCornerShape(12.dp))
                        ) {
                            Text(
                                text = key,
                                fontSize = 20.sp,
                                color = textColor,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.ubuntu_bold)) // Fonte personalizada
                            )
                        }
                    }
                }
            }
        }
    }
}
