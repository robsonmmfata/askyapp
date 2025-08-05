package br.com.smartpos.asky.ui.components.itens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavHostController
import br.com.smartpos.asky.R
import br.com.smartpos.asky.data.model.ProdutoItens
import coil.compose.rememberAsyncImagePainter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal

@SuppressLint("SuspiciousIndentation")
@Composable
fun ItensCategoria(
    gridState: LazyGridState,
    cagetoriaItem: String,
    indexDestino: Int,
    onCloseDialog: () -> Unit
) {

    val categoria: String? = cagetoriaItem
    val coroutineScope = rememberCoroutineScope()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                coroutineScope.launch {
                    gridState.scrollToItem(indexDestino) // Rola até o índice da categoria

                }
                onCloseDialog()
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),

        ) {
        ConstraintLayout(

        ) {

            Text(
                text = categoria!!,
                fontSize = 20.sp,
                color = Color.Black,
                maxLines = 1,  // Limita a apenas uma linha
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
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