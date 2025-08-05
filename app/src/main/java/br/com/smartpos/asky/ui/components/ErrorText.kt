package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R

@Composable
fun ErrorText(
    message: String?,
    modifier: Modifier = Modifier,
    color: Color = Color.Red,
) {
    if (message != null) {
        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily(Font(R.font.ubuntu_bold)),
                fontSize = 20.sp,
                color = color,
            ),
            modifier = modifier
                .padding(top = 8.dp)
        )
    }
}
