package br.com.smartpos.asky.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.ui.theme.ColorBlueOne
import br.com.smartpos.asky.ui.theme.ColorGradientButton
import br.com.smartpos.asky.ui.theme.ColorGradientTop
import br.com.smartpos.asky.ui.theme.ColorWarning


// Componente de botão de pagamento
@Composable
fun ButtonPesquisa(
    text: String,
    onClick: () -> Unit,
    height: Int = 35,
    overflow: TextOverflow = TextOverflow.Clip,
    expanded: Boolean

) {

    val rotationAngle by animateFloatAsState(if (expanded) 180f else 0f, label = "")

    Box( // 🔹 Envolvendo o botão para evitar que a borda afete o tamanho
        modifier = Modifier
            .width(if (!expanded) 100.dp else 120.dp)
            .height(height.dp) // Mantém a altura correta
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color.LightGray, Color.LightGray)
                ),
                shape = RoundedCornerShape(8.dp)
            )

    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (!expanded) Color.White else Color(
                    0xFFF0F0F0
                )
            ) // Cor de fundo do botão
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = overflow,
                    color = Color.Black,
                    modifier = Modifier.weight(0.8f)
                        .padding(start = 4.dp),
                    textAlign = TextAlign.Center,
                )
                if (expanded) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = null,
                        modifier = Modifier
                            .size(26.dp)
                            .graphicsLayer(rotationZ = rotationAngle)// Rotação animada
                            .weight(0.2f),
                    )
                }
            }
        }
    }
}
