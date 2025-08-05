package br.com.smartpos.asky.ui.components.itens

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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MesaBottomSheet(
    sheetState: SheetState,
    navController: NavHostController,
    scope: CoroutineScope,
    showBottomSheetMesa: MutableState<Boolean>,
    qtConvidado: String,
    idmesa: String,
    ){

    var showBottomSheet by remember { mutableStateOf(false) }
    var qtConvidados by remember { mutableStateOf(qtConvidado) }
    var idMesa by remember { mutableStateOf(idmesa) }

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
                                .clickable {
                                    scope
                                        .launch { sheetState.hide() }
                                        .invokeOnCompletion {
                                            if (!sheetState.isVisible) {
                                                showBottomSheetMesa.value =
                                                    false
                                            }
                                        }
                                }
                        ) {
                            Text(
                                text = "x",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp, 6.dp, 1.dp, 1.dp )
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Mesa",
                            color = Color.Black, // Texto branco semi-transparente
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(22.dp))


                        val textFieldModifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(8.dp)
                        OutlinedTextField(
                            value = qtConvidados,
                            onValueChange = { newValue ->
                                // Permite apenas números e evita erro ao converter
                                if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                                    qtConvidados = newValue
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = textFieldModifier,
                            shape = RoundedCornerShape(25),
                            label = {
                                Text(text = "Total de convidados")
                            }
                        )

                        OutlinedTextField(
                            value = idMesa,
                            onValueChange = { newValue ->
                                // Permite apenas números e evita erro ao converter
                                if (newValue.all { it.isDigit() } || newValue.isEmpty()) {
                                    idMesa = newValue
                                }
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            modifier = textFieldModifier,
                            shape = RoundedCornerShape(25),
                            label = {
                                Text(text = "Número da mesa")
                            },
                        )
                        Spacer(modifier = Modifier.height(22.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp, 0.dp, 30.dp, 0.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,

                            ) {



                            Card(

                                modifier = Modifier
                                    .fillMaxWidth()
                                    .size(width = 240.dp, height = 50.dp)
                                    .clickable {

                                        scope
                                            .launch { sheetState.hide() }
                                            .invokeOnCompletion {
                                                if (!sheetState.isVisible) {
                                                    showBottomSheetMesa.value =
                                                        false
                                                }
                                            }
                                        navController.navigate("produto/$idMesa/$qtConvidados")
                                    }
                                    .padding(10.dp, 0.dp, 5.dp, 0.dp),

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

                                    ) {

                                    Text(
                                        text = "Buscar",
                                        fontSize = 16.sp,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp),
                                        textAlign = TextAlign.Center,
                                    )

                                }
                            }
                        }

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

            }
        }
    }
}