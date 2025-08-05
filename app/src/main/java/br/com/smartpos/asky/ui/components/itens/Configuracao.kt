package br.com.smartpos.asky.ui.components.itens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import br.com.smartpos.asky.viewModel.MenuViewModel

@Composable
fun Configuracao(
    viewModel: MenuViewModel,
    onConfig2: () -> Unit,
    onConfig3: () -> Unit
){

    val imagemCol2 = remember { mutableStateOf(if(viewModel.getColuna()==2) R.drawable.ic_col2_blue else R.drawable.ic_col2_gray) }
    val imagemCol3 = remember { mutableStateOf(if(viewModel.getColuna()==2) R.drawable.ic_col3_gray else  R.drawable.ic_col3_blue) }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            modifier = Modifier
                .padding(end = 20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 0.dp, topEnd = 16.dp,
                        bottomEnd = 0.dp, bottomStart = 0.dp
                    )
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                "Configurações",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.ubuntu_regular)),
                modifier = Modifier.padding(top = 10.dp, end = 20.dp)
            )

        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(end = 20.dp)
                .background(Color.White)
                .fillMaxWidth(),

            ) {
            Text(
                "colunas",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(start = 10.dp, top = 20.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .padding(end = 20.dp)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(
                        topStart = 0.dp, topEnd = 0.dp,
                        bottomEnd = 16.dp, bottomStart = 0.dp
                    )
                )
                .fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(imagemCol2.value),
                contentDescription = "2 Colunas",
                modifier = Modifier
                    .size(114.dp)
                    .clip(CircleShape)
                    .clickable {
                        onConfig2()
                        viewModel.saveColuna(2)
                        imagemCol2.value =
                            R.drawable.ic_col2_blue // Imagem ativa
                        imagemCol3.value =
                            R.drawable.ic_col3_gray  // Reseta outra imagem
                    }
                    .padding(8.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Ícone para 3 colunas
            Image(
                painter = painterResource(imagemCol3.value),
                contentDescription = "3 Colunas",
                modifier = Modifier
                    .size(114.dp)
                    .clip(CircleShape)
                    .clickable {
                        onConfig3()
                        viewModel.saveColuna(3)
                        imagemCol2.value =
                            R.drawable.ic_col2_gray  // Reseta outra imagem
                        imagemCol3.value =
                            R.drawable.ic_col3_blue // Imagem ativa
                    }
                    .padding(8.dp)
            )
        }
    }
}