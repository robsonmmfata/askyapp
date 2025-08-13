package br.com.smartpos.asky.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.smartpos.asky.ui.screens.QRCodeScannerScreen
import br.com.smartpos.asky.viewModel.QRCodeViewModel

@Composable
fun QRCodeNavigation(
    navController: NavHostController,
    qrCodeViewModel: QRCodeViewModel
) {
    composable("qr_scanner") {
        QRCodeScannerScreen(
            onQRCodeScanned = { code ->
                qrCodeViewModel.verifyQRCode(code)
                navController.navigate("login_success") {
                    popUpTo("qr_scanner") { inclusive = true }
                }
            },
            onBack = { navController.popBackStack() }
        )
    }
}
