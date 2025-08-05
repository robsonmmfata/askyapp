package br.com.smartpos.asky.ui.screens

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.data.model.SCList
import br.com.smartpos.asky.data.model.pedido.Adicionais
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.ui.components.ButtonAdicionar
import br.com.smartpos.asky.ui.components.ButtonPesquisa
import br.com.smartpos.asky.ui.components.CheckboxAdicionais
import br.com.smartpos.asky.ui.components.Pesquisa
import br.com.smartpos.asky.ui.components.SpinnerQuantidade
import br.com.smartpos.asky.ui.components.itens.Configuracao
import br.com.smartpos.asky.ui.components.itens.EstrelaBottonSheet
import br.com.smartpos.asky.ui.components.itens.GridProduto
import br.com.smartpos.asky.ui.components.itens.ItensCategoria
import br.com.smartpos.asky.ui.components.itens.ItensProduto
import br.com.smartpos.asky.ui.components.itens.MesaBottomSheet
import br.com.smartpos.asky.ui.components.itens.PaginaMenu
import br.com.smartpos.asky.utils.formatToCurrency
import br.com.smartpos.asky.viewModel.MenuViewModel
import coil.compose.rememberAsyncImagePainter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProdutoScreen(
    navController: NavHostController,
    modifier: Modifier,
    viewModel: MenuViewModel,
    idmesa: Int?,
    qtConvit: Int?,
){

    var expandedA = remember { mutableStateOf(viewModel.getPagA()) }
    var expandedB = remember { mutableStateOf(viewModel.getPagB()) }
    val produto by viewModel.produto.collectAsState()
    val menu by viewModel.menu.collectAsState()
    val pedidos by viewModel.pedidos.collectAsState()
    var qt = remember { mutableIntStateOf(1) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded  = true)
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }
    var showBottomSheetEstrela by remember { mutableStateOf(false) }
    var priceAdd by remember { mutableDoubleStateOf(0.0) }
    var numColunas = remember { mutableStateOf(viewModel.getColuna()) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val opcaoSelecionada = remember { mutableStateOf(viewModel.getColuna()) }
    var mostrarRow by remember { mutableStateOf(false) }
    var busca by remember { mutableStateOf(false) }
    var configuracao by remember { mutableStateOf(false) }
    var menubtn by remember { mutableStateOf(0) }
    var pagina by remember { mutableIntStateOf(0) }
    val adicionaisList = remember { MutableStateFlow<ArrayList<Adicionais>?>(arrayListOf())}
    val adicionaisSelecionados = remember { mutableStateMapOf<String, Boolean>()}
    var searchText by remember { mutableStateOf("") }
    val pagerState = rememberPagerState( initialPage = viewModel.getMenuPrincipal() ) { menu.size }
    val coroutineScope = rememberCoroutineScope()
    var filtro by remember { mutableStateOf("") }
    var novoProduto = remember { mutableStateOf("") }
    var valorProduto by remember { mutableStateOf("") }
    var valorDouble = remember { mutableStateOf(0.0) }
    var pesquisar by remember { mutableStateOf("") }
    var qtConvidados by remember { mutableStateOf("") }
    var idMesa by remember {  mutableStateOf("") }
    var showBottomSheetMesa = remember { mutableStateOf(false) }
    val clientes by viewModel.cliente.collectAsState()
    var titulo = remember { mutableStateOf(viewModel.getTitulo()) }
    var qtAcicionais = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        if(idMesa=="") idMesa = idmesa.toString()
        if(qtConvidados=="") qtConvidados = qtConvit.toString()
        viewModel.getMenu()
    }


    LaunchedEffect(pagerState.currentPage) {
        searchText = ""
        pagina = pagerState.currentPage
    }



    val produtoListaComida: Map<String, List<ProdutoItens>> = viewModel.getComidas(pagina)
    val categoryListComida = produtoListaComida.map {
        SCList(
            name = it.key,
            items = it.value
        )
    }

    val produtoListaBebida: Map<String, List<ProdutoItens>> = viewModel.getBebidas(pagina)
    val categoryListBebida = produtoListaBebida.map {
        SCList(
            name = it.key,
            items = it.value
        )
    }

//    val mapaTitulosComida: MutableMap<String, Int> = remember { viewModel.getMapa(categoryListComida) }
//    val mapaTitulosBebida: MutableMap<String, Int> = remember { viewModel.getMapa(categoryListBebida) }


    val listaFiltradaComida = produtoListaComida.mapValues { (_, produtos) ->
        produtos.filter { it.nomeProduto!!.contains(searchText, ignoreCase = true) }
    }.filterValues { it.isNotEmpty() }

    val listaFiltradaBebidas = produtoListaBebida.mapValues { (_, produtos) ->
        produtos.filter { it.nomeProduto!!.contains(searchText, ignoreCase = true) }
    }.filterValues { it.isNotEmpty() }

    LaunchedEffect(produto) {
        if (produto != null) {
            showBottomSheet = true
        }
        if( pedidos.size > 0) mostrarRow = true

    }

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = 50.dp)) {

        Scaffold() {
            Column (modifier = Modifier.background(color =  Color.White)){

                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .padding(top = 60.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    if (busca) {
                        OutlinedTextField(
                            value = searchText,
                            onValueChange = { searchText = it },
                            label = { Text("Buscar...") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 20.dp)
                        )
                        Box(
                            modifier = Modifier
                                .padding(end = 10.dp, top = 15.dp, start = 10.dp)
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFA3E1FD))
                                .clickable {
                                    searchText = ""
                                    busca = !busca
                                }
                        ) {
                            Text(
                                text = "x",
                                fontSize = 20.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp, 6.dp, 1.dp, 1.dp)
                            )
                        }
                    }

                    if (!busca) {
                        Text("Menu",
                            color = Color.Black,
                            modifier = Modifier.padding(start = 20.dp, top = 12.dp, end = 20.dp))

                        LazyRow(
                            modifier = Modifier.weight(1f), // Ocupa o espaço disponível para rolar
                            horizontalArrangement = Arrangement.spacedBy(4.dp) // Espaçamento entre os botões
                        ) {
                            items(menu.size) { index ->

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFFFFFF)
                                    ),
                                    onClick = {
                                        configuracao = false
                                        coroutineScope.launch {
                                            pagerState.animateScrollToPage(
                                                page = index,
                                                animationSpec = tween(durationMillis = 600, easing = LinearOutSlowInEasing)
                                            )
                                        }
                                        viewModel.saveMenuPrincipal(index)
                                        menubtn = index
                                        filtro = ""
                                    },
                                    modifier = Modifier.padding(start = if (index == 0) 10.dp else 0.dp)
                                ) {
                                    Text(menu[index].uppercase(),
                                        style = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            color = if (menubtn == index)  Color.Black else Color.LightGray // Define a cor do texto aqui
                                        )
                                    )
                                }
                            }
                        }

                        Button(
                            onClick = { showBottomSheetEstrela = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black // 🔹 Agora o ícone fica visível
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Criar item"
                            )
                        }
                        // Botões à direita (End)
                        Row(modifier = Modifier.padding(end = 10.dp)) {
                            Button(
                                onClick = { busca = true },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFFFFFFF),
                                    contentColor = Color.Black // 🔹 Ícone visível agora
                                ),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.size(40.dp),
                                contentPadding = PaddingValues(0.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = "Buscar",
                                    modifier = Modifier
                                        .size(30.dp)
                                        .padding(top = 8.dp)
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                }

                // 🔹 Pager (ViewPager)
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize(),
                    userScrollEnabled = false
                ) { page ->

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = 10.dp)
                        ) {
                            ModalNavigationDrawer(
                                drawerState = drawerState,
                                drawerContent = {
                                    if(configuracao) {
                                        Configuracao(
                                            viewModel = viewModel,
                                            onConfig2 = {
                                                opcaoSelecionada.value = 2
                                                numColunas.value = 2},
                                            onConfig3 = {
                                                opcaoSelecionada.value = 3
                                                numColunas.value = 3
                                            }
                                        )
                                    }
                                }
                            ) {
                                PaginaMenu(
                                    navController = navController,
                                    qtConvidado = qtConvidados,
                                    idmesa = idMesa,
                                    viewModel = viewModel,
                                    modifier = modifier,
                                    categoryListComida = categoryListComida,
                                    categoryListBebida = categoryListBebida,
                                    listaFiltradaComida = listaFiltradaComida,
                                    listaFiltradaBebidas = listaFiltradaBebidas,
                                    numcolunas = numColunas,
                                    expandedA = expandedA.value,
                                    expandedB = expandedB.value,
                                    titulo = titulo.value,
                                    tituloAtualBebida = "Bebidas",
                                    onClickMesa = { showBottomSheetMesa.value = true }
                                )
                                
                                if (mostrarRow) {
                                    Row(

                                        modifier = Modifier
                                            .align(Alignment.BottomCenter) // Fixa o botão na parte inferior central da tela
                                            .padding(16.dp)
                                            .background(Color.Transparent) // Transparente
                                            .fillMaxWidth(0.8f), // Define a largura do botão
                                        horizontalArrangement = Arrangement.SpaceBetween,

                                        ) {

                                        Button(
                                            onClick = {scope.launch { drawerState.open() }; configuracao = true },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White,
                                                contentColor = Color.Black // 🔹 Agora o ícone fica visível
                                            ),
                                            shape = CircleShape, // Deixa o botão redondo
                                            modifier = Modifier.size(50.dp),
                                            contentPadding = PaddingValues(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Settings,
                                                contentDescription = "Abrir Menu",
                                                tint = Color(0xFF2196F3),
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }

                                        Card(

                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .size(width = 240.dp, height = 50.dp)
                                                .clickable {
                                                    navController.navigate("pedido/$idmesa")
                                                    viewModel.limparProduto()
                                                }
                                                .padding(10.dp, 0.dp, 0.dp, 0.dp),

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
                                                    text = "Ver carrinho",
                                                    fontSize = 16.sp,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .padding(20.dp, 12.dp, 5.dp, 0.dp),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Right,
                                                )

                                                Text(
                                                    text = formatToCurrency(viewModel.getTotal(pedido = pedidos )),
                                                    fontSize = 16.sp,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .padding(5.dp, 12.dp, 10.dp, 0.dp),
                                                    textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                                )

                                            }
                                        }
                                    }

                                } else {
                                    Row(

                                        modifier = Modifier
                                            .align(Alignment.BottomStart)
                                            .padding(16.dp)
                                            .background(Color.Transparent) // Transparente
                                            .fillMaxWidth(0.8f), // Define a largura do botão
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                    ){
                                        Button(
                                            onClick = {scope.launch { drawerState.open() }; configuracao = true },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color.White,
                                                contentColor = Color.Black // 🔹 Agora o ícone fica visível
                                            ),
                                            shape = CircleShape, // Deixa o botão redondo
                                            modifier = Modifier.size(50.dp),
                                            contentPadding = PaddingValues(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Settings,
                                                contentDescription = "Abrir Menu",
                                                tint = Color(0xFF2196F3),
                                                modifier = Modifier.size(32.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                }

                if (showBottomSheet) {

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
                            val valorB:Double? = produto?.price
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
                                            .padding(
                                                10.dp,
                                                0.dp,
                                                10.dp,
                                                0.dp
                                            ),
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
                                                                showBottomSheet =
                                                                    false
                                                            }
                                                        }
                                                    viewModel.limparProduto()
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
                                    Image(
                                        painter = rememberAsyncImagePainter(
                                            model = produto?.photo,
                                            placeholder = painterResource(R.drawable.placeholder),
                                            error = painterResource(R.drawable.placeholder)
                                        ),
                                        contentDescription = "Produto",
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .size(
                                                height = 250.dp,
                                                width = 200.dp
                                            )
                                            .clip(RoundedCornerShape(10)),
                                        contentScale = ContentScale.Fit
                                    )

                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                10.dp,
                                                0.dp,
                                                10.dp,
                                                0.dp
                                            ),
                                        horizontalArrangement = Arrangement.Center,
                                    ) {
                                        if (produto?.waiting_minutes.toString() != "0") {
                                            Box(
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .background(Color(0xFF2196F3)),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = "Preparação: " + produto?.waiting_minutes.toString() + " minutos",
                                                    fontSize = 14.sp,
                                                    color = Color.White,
                                                    modifier = Modifier
                                                        .padding(
                                                            20.dp,
                                                            4.dp,
                                                            20.dp,
                                                            4.dp
                                                        ),
                                                    textAlign = TextAlign.Center,
                                                    fontFamily = FontFamily(Font(R.font.ubuntu_regular))
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    Text(
                                        text = produto?.name.toString(),
                                        fontSize = 25.sp,
                                        color = Color.Black,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(
                                                20.dp,
                                                8.dp,
                                                20.dp,
                                                8.dp
                                            ),
                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                        fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                                    )


                                    produto?.let { it1 ->
                                        if (it1.description != null)
                                            Text(
                                                text = it1.description ?: "",
                                                fontSize = 16.sp,
                                                color = Color.Black,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        20.dp,
                                                        8.dp,
                                                        20.dp,
                                                        8.dp
                                                    ),
                                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                                fontFamily = FontFamily(Font(R.font.ubuntu_regular))
                                            )
                                    }
                                    Spacer(modifier = Modifier.height(5.dp))
                                    produto?.let { it2 ->
                                        Text(
                                            text = formatToCurrency(it2.price),
                                            fontSize = 25.sp,
                                            color = Color.Black,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    20.dp,
                                                    8.dp,
                                                    20.dp,
                                                    8.dp
                                                ),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                                        )
                                    }
                                    if ((produto?.extra_lists?.size ?: 0) > 0) {
                                        Text(
                                            text = "Adicionais",
                                            fontSize = 25.sp,
                                            color = Color.Black,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    20.dp,
                                                    8.dp,
                                                    20.dp,
                                                    0.dp
                                                ),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
                                        )
                                        Text(
                                            text = "Selecione 2-3",
                                            fontSize = 18.sp,
                                            color = Color.Black,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(
                                                    20.dp,
                                                    2.dp,
                                                    20.dp,
                                                    8.dp
                                                ),
                                            textAlign = androidx.compose.ui.text.style.TextAlign.Left,
                                            fontFamily = FontFamily(Font(R.font.ubuntu_regular))
                                        )
                                        Spacer(modifier = Modifier.height(5.dp))

                                        produto?.extra_lists!!.forEach { extra ->
                                            var maximo = extra.max
                                            var minimo = extra.min
                                            var multiplo = extra.multiple
                                            extra.extras.forEach { add ->
                                                var isSelected = adicionaisSelecionados[add.name] ?: false
                                                val totalSelecionados = extra.extras.count { adicionaisSelecionados[it.name] == true }
                                                CheckboxAdicionais(
                                                    isSelected = isSelected,
                                                    price = add.price,
                                                    txtAdicional = add.name,
                                                    onCheckedChange = { isChecked ->

                                                        // Se não for múltiplo, só pode um
                                                        if (!multiplo) {
                                                            // Desmarca todos
                                                            extra.extras.forEach {
                                                                adicionaisSelecionados[it.name] = false
                                                            }

                                                            // Marca só o atual
                                                            adicionaisSelecionados[add.name] = true

                                                            // Recalcula o preço
                                                            priceAdd = add.price // só um item, então zera e soma o único
                                                            return@CheckboxAdicionais
                                                        }

                                                        if (isChecked && totalSelecionados >= maximo) {
                                                            // Já atingiu o máximo, não permite selecionar mais
                                                            return@CheckboxAdicionais
                                                        }
                                                        if (!isChecked && totalSelecionados <= minimo) {
                                                            // Já atingiu o mínimo, não permite desmarcar mais
                                                            return@CheckboxAdicionais
                                                        }
                                                        adicionaisSelecionados[add.name] = isChecked;
                                                        if (isChecked) {
                                                            priceAdd = priceAdd + add.price
                                                        } else {
                                                            priceAdd = priceAdd - add.price
                                                        }
                                                    }

                                                )
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
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp, 0.dp, 20.dp, 0.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,

                                    ) {

                                    SpinnerQuantidade(onValueChange = { qt.value = it })

                                    valorB?.let { it1 ->
                                        ButtonAdicionar(
                                            produtoValor = it1,
                                            qt = qt.value,
                                            priceAdd = priceAdd,
                                            onClick = {
                                                produto?.extra_lists!!.forEach { extra ->
                                                    extra.extras.forEach { add ->
                                                        if (adicionaisSelecionados.containsKey(
                                                                add.name
                                                            )
                                                        ) {
                                                            val addic = Adicionais(
                                                                id = add.id,
                                                                name = add.name,
                                                                price = add.price,
                                                                quantidade = qt.value,
                                                            )
                                                            adicionaisList.value?.let { lista ->
                                                                lista.add(addic)
                                                                adicionaisList.value =
                                                                    ArrayList(lista)
                                                            }
                                                        }
                                                    }
                                                }

                                                val novoPedido = produto?.let {
                                                    adicionaisList.value?.let { it1 ->
                                                        PedidoItens(
                                                            idMesa = idmesa!!.toInt(),
                                                            idProduto = it.id,
                                                            name = it.name,
                                                            qt = qt.value,
                                                            price = (produto!!.price + priceAdd) * qt.value,
                                                            obs = "",
                                                            it1
                                                        )
                                                    }
                                                }

                                                novoPedido?.let {
                                                    viewModel.addPedido(it)
                                                }
                                                if (viewModel.sizePedido() > 0) mostrarRow =
                                                    true else mostrarRow = false
                                                scope
                                                    .launch { sheetState.hide() }
                                                    .invokeOnCompletion {
                                                        if (!sheetState.isVisible) {
                                                            showBottomSheet = false
                                                        }
                                                    }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (showBottomSheetEstrela) {
                    EstrelaBottonSheet(
                        sheetState = sheetState,
                        novoproduto = novoProduto,
                        valorDouble = valorDouble,
                        valorproduto = valorProduto,
                        qt = qt,
                        mostrarrow = mostrarRow,
                        onClick = {
                            val novoPedido = adicionaisList.value?.let { it1 ->
                                PedidoItens(
                                    idMesa = idmesa!!,
                                    idProduto = 0,
                                    name = novoProduto.value,
                                    qt = qt.value,
                                    price = valorDouble.value * qt.value,
                                    obs = "",
                                    it1
                                )
                            }

                            novoPedido?.let { viewModel.addPedido(it) }


                            if (viewModel.sizePedido() > 0) mostrarRow =
                                true else mostrarRow = false


                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheetEstrela = false
                                    }
                                }
                        },
                        onClose = {

                            scope
                                .launch { sheetState.hide() }
                                .invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheetEstrela =
                                            false
                                    }
                                }

                        }

                    )

                }

                if (showBottomSheetMesa.value) {
                    
                    MesaBottomSheet(
                        sheetState = sheetState,
                        navController = navController,
                        scope = scope,
                        showBottomSheetMesa = showBottomSheetMesa,
                        qtConvidado = qtConvidados,
                        idmesa = idMesa
                    )
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
}



