package br.com.smartpos.asky.ui.components.itens

import android.annotation.SuppressLint
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.ProdutoItens
import br.com.smartpos.asky.ui.theme.ColorGradientButton
import br.com.smartpos.asky.ui.theme.ColorGradientTop
import coil.compose.rememberAsyncImagePainter
import java.math.BigDecimal

@SuppressLint("SuspiciousIndentation")
@Composable
fun ItensProduto(
    navController: NavHostController,
    produtoItens: ProdutoItens,
    onClick: () -> Unit,
){

    val imagem: String? = produtoItens.foto
    val valor: BigDecimal? = produtoItens.valor
    val produto: String? = produtoItens.nomeProduto
    val esgotado: Boolean? = produtoItens.esgotado

    Card(
        modifier = Modifier
            .fillMaxSize(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = ShapeDefaults.Medium, // Cantos arredondados
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp), // Sombra
    ) {
        ConstraintLayout(

        ) {

            val (
                imgProduto,
                txtProduto,
                txtPrice
            ) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .aspectRatio(1f) // Mantém a proporção quadrada
                    .constrainAs(imgProduto) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        end.linkTo(parent.end)
                    }
            ) {
                // Imagem principal
                Image(
                    painter = rememberAsyncImagePainter(
                        model = imagem,
                        placeholder = painterResource(R.drawable.placeholder),
                        error = painterResource(R.drawable.placeholder)
                    ),
                    contentDescription = "Produto",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                if(esgotado == true) {
                    // Marca d'água (fundo cinza transparente + texto centralizado)
                    Box(
                        modifier = Modifier
                            .fillMaxSize() // Cobre toda a imagem
                            .background(Color.Black.copy(alpha = 0.3f)) // Fundo cinza transparente
                            .align(Alignment.Center) // Garante que o Box fique centralizado
                    ) {
                        Text(
                            text = "Esgotado",
                            color = Color.White.copy(alpha = 0.7f), // Texto branco semi-transparente
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.Center) // Centraliza o texto
                        )
                    }
                }
            }
                Text(
                    text = "R$${valor ?: "0.00"}",
                    fontSize = 12.sp,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    ),

                    modifier = Modifier
                        .constrainAs(txtPrice) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .background(
                            color = Color(0xFFE9E7E7),
                            shape = RoundedCornerShape(
                                topStart = 0.dp, topEnd = 0.dp, // Apenas canto superior direito arredondado
                                bottomEnd = 0.dp, bottomStart = 8.dp
                            )
                        )
                        .padding(4.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Right,

                )
                Text(
                    text = produto ?: "",
                    fontSize = 14.sp,
                    style = TextStyle(
                        color = Color.Black
                    ),
                    maxLines = 1,  // Limita a apenas uma linha
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .constrainAs(txtProduto) {
                            //top.linkTo(imgProduto.bottom) // Abaixo do Texto 1
                            end.linkTo(parent.end) // Alinhado à direita
                            bottom.linkTo(parent.bottom)
                        }
                        .background(Color(0xFFE9E7E7))
                        .fillMaxWidth()
                        .padding(top = 4.dp, start = 4.dp),

                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }
}

//@Preview
//@Composable
//fun ItensProdutoPreview(){
//    ItensProduto()
//}