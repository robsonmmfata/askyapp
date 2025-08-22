package br.com.smartpos.asky.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.viewModel.MenuViewModel // Verifique se este é o caminho correto para MenuViewModel

@Composable
fun ProdutoScreen(
    navController: NavHostController, // Parâmetro está aqui e correto
    modifier: Modifier = Modifier,
    viewModel: MenuViewModel, // Verifique se MenuViewModel é o tipo correto e importado corretamente
    idmesa: Int?,
    qtConvit: Int?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Tela de Produto")
        Spacer(modifier = Modifier.height(8.dp))
        Text("ID Mesa: $idmesa")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Quantidade Convite: $qtConvit")
        // Button(onClick = { navController.popBackStack() }) {
        // Text("Voltar")
        // }
    }
}
