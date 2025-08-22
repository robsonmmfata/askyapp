package br.com.smartpos.asky.navigation

import android.util.Log
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.smartpos.asky.data.remote.api.AskyApi // Import adicionado
import br.com.smartpos.asky.ui.screens.ClienteScreen
import br.com.smartpos.asky.ui.screens.LoginScreen
import br.com.smartpos.asky.ui.screens.MesaScreen
import br.com.smartpos.asky.ui.screens.PedidoScreen
import br.com.smartpos.asky.ui.screens.PrincipalScreen
import br.com.smartpos.asky.ui.screens.ProdutoScreen
import br.com.smartpos.asky.ui.screens.QRCodeScannerScreen
// !!! IMPORTANTE: Verifique se 'viewModel' é o nome correto do pacote.
// Ex: br.com.smartpos.asky.features.menu.viewModel.MenuViewModel
import br.com.smartpos.asky.viewModel.MenuViewModel // Cuidado com este import se o pacote estiver errado
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavGraph(
    navController: NavHostController,
    menuViewModel: MenuViewModel,
    askyApi: AskyApi // Parâmetro adicionado
) {

    LaunchedEffect(Unit) {
        menuViewModel.navigateToLogin
            .onEach {
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }
            .launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) }
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = menuViewModel,
                modifier = Modifier,
                onEnterLogin = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                },
            )
        }

        composable("qr_scanner") {
            QRCodeScannerScreen(
                // Removido: navController = navController,
                // Removido: viewModel = menuViewModel,
                onQRCodeScanned = { scannedData ->
                    // TODO: Implemente a lógica real para quando um QR Code for escaneado.
                    // Por exemplo, você pode querer navegar para outra tela,
                    // ou chamar uma função no seu menuViewModel (que está disponível neste escopo do NavGraph).
                    Log.d("NavGraph", "QR Code Scanned: $scannedData")
                    // Exemplo: menuViewModel.processScannedCode(scannedData)
                    // Exemplo: navController.navigate("details/$scannedData")
                    navController.popBackStack() // Volta para a tela anterior como ação padrão
                },
                onBack = {
                    // TODO: Implemente a lógica para o botão voltar na tela do scanner.
                    navController.popBackStack() // Ação mais comum é voltar.
                }
            )
        }

        composable("main") {
            PrincipalScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable(
            route = "pedido/{Id}",
            arguments = listOf(
                navArgument(name = "Id") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val idMesa = backStackEntry.arguments?.getInt("Id")
            PedidoScreen(
                navController = navController,
                viewModel = menuViewModel,
                idMesa = idMesa
            )
        }

        composable("mesa") {
            MesaScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable("cliente") {
            ClienteScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable(
            route = "produto/{Id}/{qt}",
            arguments = listOf(
                navArgument(name = "Id") {
                    type = NavType.IntType
                },
                navArgument(name = "qt") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val idMesa = backStackEntry.arguments?.getInt("Id")
            val qt = backStackEntry.arguments?.getInt("qt")
            ProdutoScreen(
                navController = navController,
                modifier = Modifier,
                viewModel = menuViewModel,
                idmesa = idMesa,
                qtConvit = qt
            )
        }
        
        // Se você adicionar a rota para CheckoutScreen aqui,
        // poderá passar o askyApi:
        // composable("checkout/{totalValue}/{stoneId}") { backStackEntry ->
        //     val totalValue = backStackEntry.arguments?.getString("totalValue")?.toIntOrNull() ?: 0
        //     val stoneId = backStackEntry.arguments?.getString("stoneId") ?: ""
        //     CheckoutScreen(
        //         navController = navController,
        //         totalValue = totalValue,
        //         stoneId = stoneId,
        //         askyApi = askyApi // Passando a instância da API
        //     )
        // }
    }
}

fun closeAllScreens(navController: NavHostController) {
    navController.navigate("login") {
        popUpTo(0) {
            inclusive = true
        }
        launchSingleTop = true
    }
}
