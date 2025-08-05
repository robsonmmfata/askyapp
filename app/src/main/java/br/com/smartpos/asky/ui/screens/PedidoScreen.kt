package br.com.smartpos.asky.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.Desconto
import br.com.smartpos.asky.ui.components.itens.ItensPedido
import br.com.smartpos.asky.utils.formatToCurrency
import br.com.smartpos.asky.viewModel.MenuViewModel

@Composable
fun PedidoScreen(
    navController: NavHostController,
    viewModel: MenuViewModel,
    idMesa: Int?
){

    val pedidos by viewModel.pedidos.collectAsState()
    var showTextField by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedCategoria by remember { mutableStateOf<Desconto?>(null) }

    var qtConvidados by remember { mutableStateOf(0) }
    // spiner desconto
    val desconto = listOf(
        Desconto("Disconto", 0.0),
        Desconto("GST incluído", 10.0),
    )

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(bottom = 40.dp)
            .verticalScroll(rememberScrollState()),

    ) {
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 20.dp, 10.dp, 20.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Pedido",
                fontSize = 25.sp,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 8.dp, 20.dp, 0.dp),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .heightIn(min = 100.dp, max = 300.dp)
                .background(Color(0xFFFFFFFF)),
        ) {
            items(pedidos) { item ->
                ItensPedido(item)
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 20.dp, 10.dp, 20.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            OutlinedButton(
                onClick = { showTextField = !showTextField },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (showTextField) Color(0xFFF0F0F0) else Color.White, // Cinza claro quando ativo
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.LightGray),
                shape = RoundedCornerShape(12.dp),
            ) {
                val icon = if (showTextField) {
                    painterResource(id = R.drawable.ic_edit) // Ícone padrão do Material
                } else {
                    painterResource(id = R.drawable.ic_obs) // Ícone do drawable
                }

                if (showTextField) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Image(
                        painter = icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text("Observação")
            }
        }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 20.dp, 10.dp, 20.dp),
                horizontalArrangement = Arrangement.Center,
            ) {


            if (showTextField) {
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
                        .padding(4.dp),
                    placeholder = { Text("Escreva sua observação...") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Subtotal",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
            )

            Text(
                text = "R$ " + viewModel.getTotal(pedido = pedidos ),
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 20.dp),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
            )
        }

        Button(
            onClick = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF0F0F0), // Cinza claro quando ativo
                contentColor = Color.Black
            ),

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Taxas e descontos", modifier = Modifier.weight(1f))
                Text(formatToCurrency(10.0))
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expandir",
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (expanded) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(top = 4.dp)
            ) {
                desconto.forEach { desconto ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(desconto.name)
                        Text(formatToCurrency(desconto.valor))
                    }

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = "Total",
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(start = 20.dp),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
            )

            Text(
                text = "R$ " + viewModel.getTotal(pedido = pedidos ),
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 20.dp),
                fontFamily = FontFamily(Font(R.font.ubuntu_bold))
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Espaço uniforme entre os botões
        ) {
            Button(
                onClick = { navController.navigate("produto/$idMesa/$qtConvidados")},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0), // Cinza claro quando ativo
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.weight(1f) // Ocupa 50% da largura
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_carrinho),
                    contentDescription = "Carrinho",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(formatToCurrency(viewModel.getTotal(pedido = pedidos )))
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF0F0F0), // Cinza claro quando ativo
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.weight(1f) // Ocupa 50% da largura
            ) {
//                Icon(
//                    imageVector = Icons.Default.TableChart, // Ícone de mesa (altere se precisar)
//                    contentDescription = "Mesa",
//                    modifier = Modifier.size(20.dp)
//                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Mesa ($idMesa)")
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp) // Espaço uniforme entre os botões
        ) {
            Button(
                onClick = { navController.navigate("produto/$idMesa/$qtConvidados")},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE91E63), // Cinza claro quando ativo
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(0.3f) .height(50.dp), // Ocupa 50% da largura

            ) {

                Spacer(modifier = Modifier.width(8.dp))
                Text("Voltar",color = Color.White, fontSize = 12.sp)
            }

            Button(
                onClick = { },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.weight(0.7f) .height(50.dp),// Ocupa 50% da largura
            ) {
//                Icon(
//                    imageVector = Icons.Default.TableChart, // Ícone de mesa (altere se precisar)
//                    contentDescription = "Mesa",
//                    modifier = Modifier.size(20.dp)
//                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Pedir" ,color = Color.White, fontSize = 12.sp)
            }
        }
    }
}