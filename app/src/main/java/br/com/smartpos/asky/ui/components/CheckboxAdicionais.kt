package br.com.smartpos.asky.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.utils.formatToCurrency

@Composable
fun CheckboxAdicionais(
    isSelected: Boolean,
    price: Double,
    txtAdicional: String,
    onCheckedChange: (Boolean) -> Unit,

    ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxWidth()
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Checkbox(
                            checked = isSelected,
                            onCheckedChange = onCheckedChange,
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color.Gray, // Cor do checkbox quando selecionado
                                uncheckedColor = Color.Gray, // Cor do checkbox quando não selecionado
                                checkmarkColor = Color.Black // Cor do checkmark dentro do checkbox
                            ),
                            modifier = Modifier.size(70.dp) // Tamanho do checkbox

                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = txtAdicional,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                                fontSize = 20.sp,
                                color = Color.Black,
                            ),

                            )
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxWidth()
                        .background(Color.White),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "+"+formatToCurrency(price),
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                            fontSize = 20.sp,
                            color = Color.Black,
                        ),
                    )
                }
            }



}
