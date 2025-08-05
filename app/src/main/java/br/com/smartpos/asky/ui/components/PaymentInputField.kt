package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun PaymentInputField(
    value: String,
    onValueChange: (String) -> Unit,
    gradientColors: List<Color> = listOf(Color.Gray, Color.Gray),
    textStyle: TextStyle = MaterialTheme.typography.headlineLarge.copy(color = Color.White),
    enabled: Boolean = true,
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Box(
        modifier = Modifier
            .background(
                Brush.verticalGradient(gradientColors),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { newAmount ->
                if (newAmount.all { it.isDigit() || it == '.' }) {
                    onValueChange(newAmount)
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            textStyle = textStyle,
            keyboardActions = keyboardActions,
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .focusable(true)
        )
    }
}
