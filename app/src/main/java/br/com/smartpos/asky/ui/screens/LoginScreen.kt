package br.com.smartpos.asky.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.viewModel.MenuViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier,
    onEnterLogin: () -> Unit
) {
    val context = LocalContext.current
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login Asky", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { navController.navigate("qr_scanner") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login via QR Code Stone")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Button(
            onClick = onEnterLogin,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entrar sem QR Code")
        }
    }
}
