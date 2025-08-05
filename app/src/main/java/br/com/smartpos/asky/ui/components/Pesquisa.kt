package br.com.smartpos.asky.ui.components

import android.util.MutableBoolean
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.cliente.Cliente
import br.com.smartpos.asky.data.model.cliente.ClienteResponse
import br.com.smartpos.asky.data.model.cliente.Data
import coil.compose.rememberAsyncImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Pesquisa(
    clientes: List<Cliente>, // Lista de clientes
    pesquisa: String,
    navController: NavHostController,
    viewBtn: Boolean,
    showBottomSheetMesa:MutableState<Boolean>,
    onValueChange: (String) -> Unit,
    ) {

    var expanded by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    var btnAdicionar by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    val clientesFiltrados = remember(pesquisa) {
        if (pesquisa.isNotEmpty()) {
            clientes.filter { it.name.contains(pesquisa, ignoreCase = true) }
        } else {
            emptyList()
        }
    }



    Column {
        OutlinedTextField(
            value = pesquisa,
            onValueChange = {
                onValueChange(it)
                expanded = it.isNotEmpty() // Exibe a lista quando houver texto
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            shape = RoundedCornerShape(25),
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_lupa),
                    contentDescription = "Buscar cliente",
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text(text = "Cliente/Mesa", fontSize = 14.sp) },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
        )
        if(btnAdicionar) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp),
                horizontalArrangement = Arrangement.Center,

                ) {
                Button(
                    onClick = { navController.navigate("cliente")},
                    modifier = Modifier
                        .height(50.dp)
                        .fillMaxWidth(),
                    shape = ShapeDefaults.Small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF0F0F0)
                    )
                ) {
                    Text(
                        text = "+ Adicionar cliente",
                        fontSize = 18.sp,
                        color = Color.Black
                    )
                }

            }
        }
        if (expanded) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 1.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .heightIn(max = 300.dp) // Limita a altura para não ocupar toda a tela
                        .background(Color.White)
                ) {
                    items(clientesFiltrados) { cliente ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(1.dp)
                                .clickable {
                                    onValueChange(cliente.name) // Atualiza o campo com o nome
                                    expanded = false // Fecha a lista
                                    focusManager.clearFocus() // Remove o foco para fechar o teclado
                                    showBottomSheetMesa.value = true
                                    btnAdicionar = false
                                },
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(cliente.image),
                                contentDescription = "Foto do cliente",
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(Color.Gray)
                            )

                            Spacer(modifier = Modifier.width(8.dp))
                            btnAdicionar = viewBtn
                            Column {
                                Text(text = cliente.name, fontSize = 16.sp)
                                cliente.phone?.let { Text(text = it, fontSize = 14.sp, color = Color.Gray) }
                                cliente.email?.let { Text(text = it, fontSize = 12.sp, color = Color.Gray) }
                            }


                        }
                        Divider()
                    }
                }
            }
        } else btnAdicionar = false
    }
}
