package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.ui.theme.ColorGradientButton
import br.com.smartpos.asky.ui.theme.ColorGradientTop


@Composable
fun SpinnerQuantidade(
    onValueChange: (Int) -> Unit,
){
    Surface(modifier = Modifier.size(50.dp)) {

        val list = listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "10")
        val currentValue = remember { mutableStateOf(list[0]) }
        val expanded = remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth()
            .background(Color(0xFFFFFFFF))
            .border(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(ColorGradientTop, ColorGradientButton) // Gradiente na borda
                ),
                shape = RoundedCornerShape(5.dp) // Borda arredondada
            ),
            ) {
            Row(modifier = Modifier
                .clickable {
                    expanded.value = !expanded.value
                }
                .clip(shape = RoundedCornerShape(5.dp))
                .align(Alignment.Center),
               ) {

                Text(
                    text = currentValue.value,
                    style = TextStyle(color = Color.Black, fontSize = 20.sp)
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    modifier = Modifier
                        .background(Color.White) // Define o fundo como branco
                        .heightIn(max = 250.dp)
                        .width(40.dp)
                ) {

                    list.forEach {
                        DropdownMenuItem(
                            text = { Text(text = it) },
                            onClick = {
                                expanded.value = false
                                currentValue.value = it
                                onValueChange(it.toInt())
                            },
                            modifier = Modifier.background(Color.White)

                        )

                    }


                }
            }
        }
    }

}