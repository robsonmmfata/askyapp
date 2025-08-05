package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.ui.theme.ColorBlueOne
import br.com.smartpos.asky.ui.theme.ColorGradientButton
import br.com.smartpos.asky.ui.theme.ColorGradientTop

// Componente de botão de pagamento
@Composable
fun ButtonAsky(
    text: String,
    onClick: () -> Unit,
    height: Int = 60,
) {
    Button(
        onClick = onClick,
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth() // Preenche a largura total
            .height(height.dp) // Altura aumentada do botão
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            ) // Sombra arredondada aumentada
            .border(
                width = 3.dp,
                brush = Brush.linearGradient(
                    colors = listOf(ColorGradientTop, ColorGradientButton) // Gradiente na borda
                ),
                shape = RoundedCornerShape(12.dp) // Borda arredondada
            ),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = ColorBlueOne) // Cor de fundo do botão
    ) {
        Text(
            text = text,
            fontSize = 24.sp, // Tamanho da fonte ajustado
            fontWeight = FontWeight.Bold,
            color = Color.White, // Cor do texto
            fontFamily = FontFamily(Font(R.font.ubuntu_bold)), // Fonte personalizada
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily(Font(R.font.ubuntu_bold)),
                fontSize = 24.sp,
                color = Color.White,
                shadow = Shadow(
                    color = Color.Black,
                    offset = Offset(16f, 16f),
                    blurRadius = 10f
                )
            )
        )
    }
}
