package br.com.smartpos.asky.ui.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.smartpos.asky.R
import coil.compose.rememberAsyncImagePainter

@Composable
fun Produto(
   // modifier: Modifier,
){
    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .padding(10.dp, 10.dp, 10.dp, 10.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp, 10.dp, 0.dp),
            horizontalArrangement = Arrangement.End,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD6D5D8))
            ){
                Text(text = "x",
                    fontSize = 20.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp, 6.dp, 1.dp, 1.dp)
                )
            }
        }
        Image(
            painter = rememberAsyncImagePainter(
                model = "https://staging123.asky.ai/storage/images/enTEapOePI6kyn4BYhIWyGiEDT0rndptK1kwZdcV.jpg",
                placeholder = painterResource(R.drawable.placeholder),
                error = painterResource(R.drawable.placeholder)
            ),
            contentDescription = "Produto",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
                .size(250.dp)
                .clip(RoundedCornerShape(10))
                ,
            contentScale = ContentScale.Crop
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color(0xFF0571C6))
            ){
                Text(text = "Preparação 10 minutos",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(20.dp,8.dp,20.dp,8.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "San Daniele",
            fontSize = 25.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "R$ 25,00",
            fontSize = 25.sp,
            color = Color.Black,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 8.dp, 20.dp, 8.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.ubuntu_bold))
        )




    }

}

@Preview
@Composable
fun ItensProdutoPreview(){
    Produto()
}