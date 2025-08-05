package br.com.smartpos.asky.ui.components.itens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.pedido.Adicionais
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.ui.components.SpinnerQuantidade
import br.com.smartpos.asky.utils.formatToCurrency
import br.com.smartpos.asky.viewModel.MenuViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstrelaBottonSheet(
    sheetState: SheetState,
    novoproduto: MutableState<String>,
    valorDouble: MutableState<Double>,
    valorproduto: String,
    qt: MutableState<Int>,
    mostrarrow: Boolean,
    onClick: () -> Unit,
    onClose: () -> Unit
){

    var showBottomSheet by remember { mutableStateOf(false) }
    var mostrarRow by remember { mutableStateOf(mostrarrow) }
    var showBottomSheetEstrela by remember { mutableStateOf(false) }
    var novoProduto by remember { mutableStateOf(novoproduto) }
    var valorProduto by remember { mutableStateOf(valorproduto) }
    var valorDouble by remember { mutableStateOf(valorDouble) }
    var qt by remember { mutableStateOf(qt) }

    ModalBottomSheet(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = LocalConfiguration.current.screenHeightDp.dp * 0.8f),
        sheetState = sheetState,
        dragHandle = {},
        onDismissRequest = {
            showBottomSheet = false
        },

        ) {

        Column(
            Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(10.dp, 10.dp, 10.dp, 10.dp),
        ) {

            Box(
                modifier = Modifier
                    .weight(0.9f)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFFFFF))
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 0.dp),
                        horizontalArrangement = Arrangement.End,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFD6D5D8))
                                .clickable { onClose() },
                        ) {
                            Text(
                                text = "x",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        14.dp,
                                        6.dp,
                                        1.dp,
                                        1.dp
                                    )
                            )
                        }
                    }
                    Image(
                        painter = painterResource(R.drawable.estrela),
                        contentDescription = "Produto",
                        modifier = Modifier
                            .fillMaxSize()
                            .size(height = 250.dp, width = 200.dp)
                            .clip(RoundedCornerShape(10)),
                        contentScale = ContentScale.Fit
                    )


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    ) {

                        Spacer(modifier = Modifier.height(5.dp))

                        val textFieldModifier = Modifier
                            .fillMaxWidth(1f)
                            .padding(8.dp)
                        OutlinedTextField(
                            value = novoProduto.value,
                            onValueChange = { newValue ->
                                novoProduto.value = newValue
                            },
                            textFieldModifier,
                            shape = RoundedCornerShape(25),
                            label = {
                                Text(text = "Nome do item")
                            }
                        )

                        Spacer(modifier = Modifier.height(5.dp))

                        val textFieldModifierValor = Modifier
                            .fillMaxWidth(1f)
                            .padding(8.dp)
                        OutlinedTextField(
                            value = valorProduto,
                            onValueChange = { newValue ->

                                // Permitir apenas números e um único separador decimal
                                val filteredValue = newValue.filterIndexed { index, char ->
                                    char.isDigit() || (char in ",." && valorProduto.count { it in ",." } == 0 && index != 0)
                                }

                                // Atualiza a variável apenas se a filtragem não excluir o separador decimal já existente
                                if (filteredValue != valorProduto) {
                                    valorProduto = filteredValue
                                }

                                // Converte para Double se possível
                                val valorConvertido = filteredValue.replace(",", ".").toDoubleOrNull()
                                if (valorConvertido != null) {
                                    valorDouble.value = valorConvertido
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                            modifier = textFieldModifierValor,
                            shape = RoundedCornerShape(25),
                            label = {
                                Text(text = "Valor do item")
                            }
                        )

                    }

                }

            }
            Box(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxWidth()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {

                    SpinnerQuantidade(onValueChange = { qt.value = it })

                    Card(

                        modifier = Modifier
                            .fillMaxWidth()
                            .size(width = 240.dp, height = 50.dp)
                            .padding(10.dp, 0.dp, 5.dp, 0.dp),
                        onClick = onClick,
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF03A9F4)
                        ),
                        shape = ShapeDefaults.Small,
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp, 0.dp, 5.dp, 0.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,

                            ) {

                            Text(
                                text = "Adicionar",
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding( 20.dp, 12.dp, 5.dp, 0.dp ),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Right,
                            )

                            Text(
                                text = formatToCurrency(valorDouble.value * qt.value),
                                fontSize = 16.sp,
                                color = Color.White,
                                modifier = Modifier
                                    .padding( 5.dp, 12.dp, 0.dp, 0.dp ),
                                textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                            )

                        }
                    }
                }
            }
        }
    }
}