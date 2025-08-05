package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AskyInput(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (keyboardOptions.keyboardType == KeyboardType.Number) {
                val cleanedValue = newValue.replace("[^0-9]".toRegex(), "")
                if (cleanedValue.length <= 8) {
                    onValueChange(cleanedValue)
                }
            } else {
                if (newValue.length <= 8) {
                    onValueChange(newValue)
                }
            }
        },
        label = { Text(label, color = Color.White) },
        singleLine = true,
        keyboardOptions = keyboardOptions,
        textStyle = MaterialTheme.typography.headlineMedium.copy(
            color = Color.White,
            fontSize = 24.sp
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = Color.Gray,
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}
