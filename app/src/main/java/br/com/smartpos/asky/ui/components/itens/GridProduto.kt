package br.com.smartpos.asky.ui.components.itens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.viewModel.MenuViewModel

@Composable
fun GridProduto(
    gridState: LazyGridState,
    numColunas: Int,
    titulo: String,
    listaFiltradaComida: Map<String, List<ProdutoItens>>,
    listaFiltradaBebidas: Map<String, List<ProdutoItens>>,
    filtro: String,
    navController: NavHostController,
    viewModel: MenuViewModel,
    onClick: () -> Unit
    ){

    var idProduto by remember { mutableIntStateOf(0) }
    var qt by remember { mutableStateOf(0) }
    var searchText by remember { mutableStateOf("")}

    LazyVerticalGrid(
        state = gridState,
        columns = GridCells.Fixed(numColunas),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        if (titulo == "Comida") {
            for ((categoria, produtosFiltrados) in listaFiltradaComida) {
                // Adiciona o título da categoria
                if(filtro.length==0) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = categoria.uppercase(),
                            fontSize = 25.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(start = 0.dp, 14.dp)
                        )
                    }

                    items(produtosFiltrados.size) { index ->
                        val produtolista = produtosFiltrados.getOrNull(index)
                        if (produtolista != null) {
                            ItensProduto(
                                navController = navController,
                                produtoItens = produtolista,
                                onClick = {
                                    idProduto = produtosFiltrados.get(index).codigo
                                    viewModel.getProduto(idProduto)
                                    qt = 1
                                    searchText = ""
                                },
                            )
                        }

                    }
                } else {
                    if(filtro ==  categoria) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = categoria.uppercase(),
                                fontSize = 25.sp,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(start = 0.dp, 14.dp)
                            )
                        }

                        items(produtosFiltrados.size) { index ->
                            val produtolista =
                                produtosFiltrados.getOrNull(index) // Evita erro de índice inválido
                            if (produtolista != null) {
                                ItensProduto(
                                    navController = navController,
                                    produtoItens = produtolista,
                                    onClick = {
                                        idProduto = produtosFiltrados.get(index).codigo
                                        viewModel.getProduto(idProduto)
                                        qt = 1
                                        searchText = ""
                                    },
                                )
                            }
                        }
                    }
                }

            }
        } else {
            for ((categoria, produtosFiltrados) in listaFiltradaBebidas) {
                // Adiciona o título da categoria
                if(filtro.length==0) {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(
                            text = categoria.uppercase(),
                            fontSize = 25.sp,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(start = 0.dp, 14.dp)
                        )
                    }

                    items(produtosFiltrados.size) { index ->
                        val produtolista =
                            produtosFiltrados.getOrNull(index) // Evita erro de índice inválido
                        if (produtolista != null) {
                            ItensProduto(
                                navController = navController,
                                produtoItens = produtolista,
                                onClick = {
                                    idProduto = produtosFiltrados.get(index).codigo
                                    viewModel.getProduto(idProduto)
                                    qt = 1
                                    searchText = ""
                                },
                            )
                        }

                    }
                } else {
                    if(filtro ==  categoria) {
                        item(span = { GridItemSpan(maxLineSpan) }) {
                            Text(
                                text = categoria.uppercase(),
                                fontSize = 25.sp,
                                style = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color.White)
                                    .padding(start = 0.dp, 14.dp)
                            )
                        }

                        items(produtosFiltrados.size) { index ->
                            val produtolista =
                                produtosFiltrados.getOrNull(index) // Evita erro de índice inválido
                            if (produtolista != null) {
                                ItensProduto(
                                    navController = navController,
                                    produtoItens = produtolista,
                                    onClick = {
                                        idProduto = produtosFiltrados.get(index).codigo
                                        viewModel.getProduto(idProduto)
                                        qt = 1
                                        searchText = ""
                                    },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
