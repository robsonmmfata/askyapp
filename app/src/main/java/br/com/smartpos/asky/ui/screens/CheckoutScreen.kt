package br.com.smartpos.asky.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.data.model.PaymentRequest // Import adicionado
import br.com.smartpos.asky.data.remote.api.AskyApi // Import adicionado
import br.com.smartpos.asky.utils.QRCodeManager // Import adicionado
import kotlinx.coroutines.launch // Import para corrotina

@Composable
fun CheckoutScreen(
    navController: NavHostController,
    totalValue: Int,
    stoneId: String,
    askyApi: AskyApi // Parâmetro adicionado
) {
    val scope = rememberCoroutineScope() // Escopo para corrotinas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Total do Pedido: R$ $totalValue", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val qrCodeValue = "Valor do pedido: R$ $totalValue"
            // Supondo que createQRCodeBitmap retorne algo (ex: Bitmap).
            // Se o qrCodeBitmap não for usado, pode ser removido.
            val qrCodeBitmap = QRCodeManager.createQRCodeBitmap(qrCodeValue)

            val paymentRequest = PaymentRequest(
                valor = totalValue,
                formaDePagamento = "QR_CODE", // Certifique-se que o tipo é o esperado pela API
                idTransacao = "transacao_${System.currentTimeMillis()}"
            )

            scope.launch { // Chamada da API dentro de uma corrotina
                try {
                    val response = askyApi.sendPayment(stoneId, paymentRequest)
                    if (response.isSuccessful) {
                        // Lógica para lidar com sucesso
                        // Ex: navController.navigate("pagamento_sucesso")
                    } else {
                        // Lógica para lidar com erro
                        // Ex: exibir uma mensagem de erro
                    }
                } catch (e: Exception) {
                    // Lidar com exceções de rede ou outras
                    // Ex: exibir uma mensagem de erro genérica
                }
            }
        }) {
            Text("Fechar Pedido")
        }
    }
}
