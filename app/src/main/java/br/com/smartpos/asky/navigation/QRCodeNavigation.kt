package br.com.smartpos.asky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost // Adicionado Import NavHost
import androidx.navigation.compose.composable
import br.com.smartpos.asky.ui.screens.QRCodeScannerScreen
import br.com.smartpos.asky.viewModel.QRCodeViewModel

// Presumindo que você tenha um Composable CheckoutScreen definido em outro lugar
@Composable
fun CheckoutScreen(navController: NavHostController, totalValue: Int, stoneId: String) {
    // ... sua implementação do CheckoutScreen
}

@Composable
fun QRCodeNavigation(
    navController: NavHostController,
    qrCodeViewModel: QRCodeViewModel
) {
    // Defina seu NavHost e construa o gráfico dentro dele
    NavHost(navController = navController, startDestination = "qr_scanner") { // Você precisa definir um startDestination
        composable("checkout/{totalValue}/{stoneId}") { backStackEntry ->
            val totalValue = backStackEntry.arguments?.getString("totalValue")?.toInt() ?: 0
            val stoneId = backStackEntry.arguments?.getString("stoneId") ?: ""
            CheckoutScreen(navController, totalValue, stoneId)
        }
        composable("qr_scanner") {
            QRCodeScannerScreen(
                onQRCodeScanned = { code ->
                    qrCodeViewModel.verifyQRCode(code)
                    navController.navigate("login_success") { // Certifique-se de que "login_success" é uma rota válida
                        popUpTo("qr_scanner") { inclusive = true }
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }
        // Adicione outros destinos composable aqui, por exemplo "login_success"
        // composable("login_success") {
        //     YourLoginSuccessScreen() // Substitua pelo seu Composable da tela de sucesso de login
        // }
    }
}
