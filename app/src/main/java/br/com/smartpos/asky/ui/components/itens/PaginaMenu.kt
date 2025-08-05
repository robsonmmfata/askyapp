package br.com.smartpos.asky.ui.components.itens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.data.model.SCList
import br.com.smartpos.asky.data.model.pedido.Adicionais
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.data.model.produto.ProdutoResponse
import br.com.smartpos.asky.ui.components.ButtonAdicionar
import br.com.smartpos.asky.ui.components.ButtonPesquisa
import br.com.smartpos.asky.ui.components.CheckboxAdicionais
import br.com.smartpos.asky.ui.components.SpinnerQuantidade
import br.com.smartpos.asky.utils.formatToCurrency
import br.com.smartpos.asky.viewModel.MenuViewModel
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaMenu(

    navController: NavHostController,
    qtConvidado: String,
    idmesa: String,
    viewModel: MenuViewModel,
    modifier: Modifier,
   // mapaTitulosComida: MutableMap<String, Int>,
    categoryListComida: List<SCList>,
    categoryListBebida: List<SCList>,
  //  mapaTitulosBebida: MutableMap<String, Int>,
    listaFiltradaComida: Map<String, List<ProdutoItens>>,
    listaFiltradaBebidas: Map<String, List<ProdutoItens>>,
    numcolunas: MutableState<Int>,
    expandedA: Boolean,
    expandedB: Boolean,
    titulo: String,
    tituloAtualBebida: String,
    onClickMesa: () -> Unit

    ){

    var expandedA by remember { mutableStateOf(expandedA) }
    var expandedB by remember { mutableStateOf(expandedB) }
    var filtro by remember { mutableStateOf("") }
    var tituloAtualComida by remember { mutableStateOf("Comidas") }
    var tituloAtualBebida by remember { mutableStateOf("Bebidas") }
    var titulo by remember { mutableStateOf(titulo) }
    var isDisplayDialog by remember {mutableStateOf(false)}
    var tituloFiltro by remember { mutableStateOf("") }
    val gridState = rememberLazyGridState()
    var numColunas by remember { mutableStateOf(numcolunas) }
    var mapaTitulosComida by remember {  mutableStateOf<Map<String, Int>>(emptyMap())  }
    var mapaTitulosBebida by remember {  mutableStateOf<Map<String, Int>>(emptyMap())  }
    var qt = remember { mutableIntStateOf(1) }


    LaunchedEffect(gridState, listaFiltradaComida, listaFiltradaBebidas) {
        mapaTitulosComida = viewModel.getMapa(categoryListComida)
        mapaTitulosBebida = viewModel.getMapa(categoryListBebida)

        snapshotFlow { gridState.firstVisibleItemIndex }
            .distinctUntilChanged()
            .collect { firstVisibleIndex ->
                if(titulo == "Comida") {
                    for ((titulo, posicao) in mapaTitulosComida) {
                        if (posicao <= firstVisibleIndex) {
                            tituloAtualComida = titulo
                        }
                    }
                } else if(titulo == "Bebida"){
                    for ((titulo, posicao) in mapaTitulosBebida) {
                        if (posicao <= firstVisibleIndex) {
                            tituloAtualBebida = titulo
                        }
                    }
                }
            }
    }

    Scaffold() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFFFF))
                .padding(0.dp, 10.dp, 0.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = { onClickMesa() },
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFF0F0F0),
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .size(50.dp, 35.dp)
                        .padding(start = 2.dp),
                    border = BorderStroke(1.dp, Color.LightGray),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text("$idmesa" )
                }
                if (!expandedA) {
                    ButtonPesquisa(
                        text = tituloAtualComida.lowercase(),
                        onClick = {
                            filtro = ""
                            expandedA = !expandedA;
                            expandedB = !expandedB;
                            viewModel.setPagA(expandedA)
                            viewModel.setPagB(expandedB)
                            viewModel.setTitulo("Comida")
                            titulo = "Comida";
                            tituloAtualBebida = "Bebidas"
                        },
                        expanded = expandedA
                    )
                } else {
                    ButtonPesquisa(
                        text = tituloAtualComida.lowercase(),
                        onClick = {
                            isDisplayDialog = true;
                            titulo = "Comida";
                            tituloAtualBebida = "Bebidas"
                            tituloFiltro = "Comidas"
                        },
                        expanded = expandedA
                    )
                }

                if (!expandedB) {
                    ButtonPesquisa(
                        text = tituloAtualBebida.lowercase(),
                        onClick = {
                            filtro = ""
                            expandedB = !expandedB;
                            expandedA = !expandedA;
                            viewModel.setPagA(expandedA)
                            viewModel.setPagB(expandedB)
                            viewModel.setTitulo("Bebida")
                            tituloAtualComida = "Comidas";
                            titulo = "Bebida"
                        },
                        expanded = expandedB,
                    )
                } else {
                    ButtonPesquisa(
                        text = tituloAtualBebida.lowercase(),
                        onClick = {
                            isDisplayDialog = true;
                            tituloAtualComida = "Comidas";
                            titulo = "Bebida"
                            tituloFiltro = "Bebidas"
                        },
                        expanded = expandedB,
                    )
                }
                Card(

                    modifier = Modifier
                        .size(width = 60.dp, height = 35.dp)
                        .clickable {
                            tituloFiltro = "Filtro"
                            isDisplayDialog = true;
                        }
                        .padding(0.dp, 0.dp, 0.dp, 0.dp),

                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFA3E1FD)
                    ),
                    shape = ShapeDefaults.Small,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 4.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 5.dp,
                                top = 10.dp,
                                end = 5.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,

                        ) {

                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Filtro",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp)) // 🔹 Ajuste do espaçamento
                        Text(
                            "Filtro",
                            style = TextStyle(
                                color = Color(0xFF0276AA),
                                fontSize = 14.sp
                            ), // 🔹 Fonte um pouco menor para caber melhor
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }

            if (isDisplayDialog) {
                Dialog(onDismissRequest = { isDisplayDialog = false },) {
                    Column(
                        modifier
                            .clip(RoundedCornerShape(10))
                            .fillMaxWidth()
                            .background(Color.White)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color(0xFFDDDDDD)),
                        ) {
                            Text(
                                fontSize = 20.sp,
                                text = tituloFiltro,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center,

                                )

                        }
                        LazyColumn(
                            Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {

                            if (titulo == "Comida") {
                                val listaModificadaComida = if (tituloFiltro == "Filtro") {
                                    listOf(SCList(name = "Sem filtro", items = emptyList())) + categoryListComida
                                } else {
                                    categoryListComida
                                }
                                items(listaModificadaComida) { categlista ->

                                    ItensCategoria(gridState = gridState,
                                        cagetoriaItem = categlista.name.toString(),
                                        indexDestino = mapaTitulosComida[categlista.name] ?: 0,
                                        onCloseDialog = {
                                            if(tituloFiltro == "Filtro") {
                                                if(categlista.name.toString() == "Sem filtro")
                                                    filtro = ""
                                                else
                                                    filtro = categlista.name.toString()
                                            }
                                            isDisplayDialog = false

                                        }
                                    )


                                }
                            } else {
                                val listaModificadaBebida = if (tituloFiltro == "Filtro") {
                                    listOf(SCList(name = "Sem filtro", items = emptyList())) + categoryListBebida
                                } else {
                                    categoryListBebida
                                }
                                items(listaModificadaBebida) { categlista ->
                                    ItensCategoria(gridState = gridState,
                                        cagetoriaItem = categlista.name.toString(),
                                        indexDestino = mapaTitulosBebida[categlista.name] ?: 0,
                                        onCloseDialog = {
                                            if(tituloFiltro == "Filtro") {
                                                if(categlista.name.toString() == "Sem filtro")
                                                    filtro = ""
                                                else
                                                    filtro = categlista.name.toString()
                                            }
                                            isDisplayDialog = false
                                        })
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = modifier.height(16.dp))
            GridProduto(
                gridState = gridState,
                numColunas = numColunas.value,
                titulo = titulo,
                listaFiltradaComida = listaFiltradaComida,
                listaFiltradaBebidas = listaFiltradaBebidas,
                filtro = filtro,
                navController = navController,
                viewModel = viewModel,
                onClick = {  }

            )
        }

    }
}