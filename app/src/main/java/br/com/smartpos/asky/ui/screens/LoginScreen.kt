package br.com.smartpos.payxyz.ui.screens

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.LoginRequest
import br.com.smartpos.asky.viewModel.MenuViewModel
import org.w3c.dom.Text


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: MenuViewModel,
    modifier: Modifier = Modifier,
    onEnterLogin: () -> Unit
//    onBack: () -> Unit,
//    onKeyboardConfig: () -> Unit,
//    onStoneId: () -> Unit,
//    onValueMax: () -> Unit,

) {
    val context = LocalContext.current
    val activity = context as? Activity
    val isLoading by viewModel.isLoadingP.collectAsState()
    val login by viewModel.login.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }

    BackHandler {}

    if (!isLoading) {
        if(login==true)
           onEnterLogin()
        else {
            showErrorDialog = true
        }
    }


    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_asky),
            contentDescription = "Logo",
            modifier = Modifier.size(250.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("Faça a leitura do\n Qr code de acesso",
            color = Color.Black,
            fontSize = 18.sp,
            textAlign = TextAlign.Center)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if(viewModel.getLotinToken().isEmpty())
                    viewModel.getToken("69W63KRH")
                else
                    onEnterLogin()
            },
           // colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
            modifier = Modifier
                .width(160.dp)
                .height(50.dp)
        ) {
            Text("Ler Qr code", color = Color.White, fontSize = 18.sp)
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = { Text(text = "Erro") },
            text = { Text(text = "Login não autorizado.") },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false; viewModel.setLoading() }) {
                    Text(text = "OK")
                }
            }
        )
    }

}



