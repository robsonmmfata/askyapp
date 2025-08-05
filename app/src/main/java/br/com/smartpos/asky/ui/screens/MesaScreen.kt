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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.LoginRequest
import br.com.smartpos.asky.data.model.Mesa
import br.com.smartpos.asky.viewModel.MenuViewModel
import org.w3c.dom.Text


@Composable
fun MesaScreen(
    navController: NavHostController,
    viewModel: MenuViewModel,

) {
    val context = LocalContext.current
    val activity = context as? Activity
    BackHandler {}
    var qtConvidados by remember { mutableIntStateOf(0) }
    var idMesa by remember {  mutableIntStateOf(0) }
    BackHandler {}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF)),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Box(
            modifier = Modifier
                .fillMaxSize() // Cobre toda a imagem
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(20.dp)
                )
        {
            Box(
                modifier = Modifier
                    .height(400.dp)// Cobre toda a imagem
                    .padding(8.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(Color.White)
                )
            {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFFFFFF)),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.TopEnd
                    ) {
                        IconButton(onClick = { navController.navigate("main")}) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Fechar")
                        }
                    }
                    Text(
                        text = "Mesa",
                        color = Color.Black, // Texto branco semi-transparente
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    val textFieldModifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(8.dp)
                    OutlinedTextField(
                        value = qtConvidados.toString(),
                        onValueChange = { newValue ->
                            qtConvidados = newValue.toInt()
                        },
                        textFieldModifier,
                        shape = RoundedCornerShape(25),
                        label = {
                            Text(text = "Total de convidados")
                        }
                    )

                    OutlinedTextField(
                        value = idMesa.toString(),
                        onValueChange = { newValue ->
                            idMesa = newValue.toInt()
                        },
                        textFieldModifier,
                        shape = RoundedCornerShape(25),
                        label = {
                            Text(text = "Número da mesa")
                        },

                        )

                    Spacer(modifier = Modifier.height(16.dp))


                    Button(
                        onClick = {
                            navController.navigate("produto/$idMesa")
                        },
                        // colors = ButtonDefaults.buttonColors(backgroundColor = Color.Black),
                        modifier = Modifier
                            .fillMaxWidth(0.8f) // 80% da largura
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2196F3),
                            contentColor = Color.Black
                        ),
                    ) {
                        Text("Próximo", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }

    }
}



