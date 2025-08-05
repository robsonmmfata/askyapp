package br.com.smartpos.asky.ui.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.ui.components.itens.ItensMesa
import br.com.smartpos.asky.data.model.Mesa
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.ui.components.Pesquisa
import br.com.smartpos.asky.ui.components.SpinnerQuantidade
import br.com.smartpos.asky.ui.dialog.MesaDialog
import br.com.smartpos.asky.utils.formatToCurrency
import br.com.smartpos.asky.viewModel.MenuViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PrincipalScreen(
    navController: NavHostController,
    viewModel: MenuViewModel,

){

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showBottomSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var hasFocus by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }
    val clientes by viewModel.cliente.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var pesquisa by remember { mutableStateOf("") }
    var showBottomSheetMesa = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var qtConvidados by remember { mutableStateOf("") }
    var idMesa by remember {  mutableStateOf("") }
    BackHandler {}
    LaunchedEffect(Unit) {
        viewModel.getMenu()
        viewModel.getClientes()
    }

//    if (showDialog) {
//        MesaDialog(onDismiss = { showDialog = false })
//    }
//    LaunchedEffect(hasFocus) {
//        if (hasFocus) {
//            showDialog = true
//        }
//    }

    // Simulação de clientes
    var pesquisar by remember { mutableStateOf("") }

    //lista teste
   val mesaLista: MutableList<Mesa> = mutableListOf()
//        Mesa(status = true,idMesa = 5,tempo = "8 minutos", inicio = "22:30", total = 50.00),
//        Mesa(status = true,idMesa = 10,tempo = "10 minutos", inicio = "10:00", total = 10.00)
//    )

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)) {

        Scaffold() {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(5.dp, 25.dp, 5.dp, 0.dp)
                    .background(Color(0xFFFFFFFF)),

                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Spacer(modifier = Modifier.height(110.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(50.dp, 0.dp, 50.dp, 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,

                    ) {
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.height(40.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Abertas",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.height(40.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Fechada",
                            fontSize = 18.sp,
                            color = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(15.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()

                        .background(Color(0xFFE7E7E7)),
                ) {
                    itemsIndexed(mesaLista) { position, mesa ->
                        ItensMesa(mesa, navController)
                    }
                }
            }



        }

        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            Box(modifier = Modifier
                .padding(end = 10.dp, top = 7.dp)) {
                Image(
                    painter = painterResource(
                        id = R.drawable.logo_asky
                    ),
                    contentDescription = "logo",
                    modifier = Modifier
                        .size(60.dp)
                )
            }
            Box(modifier = Modifier
                .padding(end = 30.dp)
                ){
                    clientes?.let {
                        Pesquisa(
                            it.data.data,
                            pesquisar,
                            navController,
                            viewBtn = true,
                            showBottomSheetMesa)
                        { novoValor -> pesquisar = novoValor
                        }
                    }
                }
        }
    }

    if (showBottomSheetMesa.value) {
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
                                        .padding(
                                            14.dp,
                                            6.dp,
                                            1.dp,
                                            1.dp
                                        )
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
                                                        showBottomSheetMesa.value = false
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
                                            text = "Próximo",
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
}

//@Preview
//@Composable
//fun PrincipalPreview(){
//    val navController: NavHostController
//    val viewModel: MenuViewModel
//}
