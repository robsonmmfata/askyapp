package br.com.smartpos.payxyz.navigation


import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import androidx.navigation.compose.composable
import br.com.smartpos.asky.data.model.LoginRequest
import br.com.smartpos.asky.data.model.pedido.PedidoItens
import br.com.smartpos.asky.ui.screens.PedidoScreen
import br.com.smartpos.asky.ui.screens.PrincipalScreen
import br.com.smartpos.asky.ui.screens.ProdutoScreen
import br.com.smartpos.asky.viewModel.MenuViewModel
import br.com.smartpos.payxyz.ui.screens.ClienteScreen
import br.com.smartpos.payxyz.ui.screens.LoginScreen
import br.com.smartpos.payxyz.ui.screens.MesaScreen
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun NavGraph(
    navController: NavHostController,
    menuViewModel: MenuViewModel,

) {

    LaunchedEffect(Unit) {
        menuViewModel.navigateToLogin
            .onEach {
                navController.navigate("login") {
                    popUpTo("main") { inclusive = true }
                }
            }
            .launchIn(this)
    }

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = { fadeIn(animationSpec = tween(700)) },
        exitTransition = { fadeOut(animationSpec = tween(700)) }
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                viewModel = menuViewModel,
                modifier = Modifier,
                onEnterLogin = {navController.navigate("main"){
                    popUpTo("login") { inclusive = true } // evita voltar para o login ao pressionar "voltar"
                    }
                },
            )
        }

        composable("main") {
            PrincipalScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable("pedido/{Id}",
                arguments = listOf(
                    navArgument(name = "Id"){
                        type = NavType.IntType
                    }
                )
            ){id-> val idMesa = id.arguments?.getInt("Id")
                PedidoScreen(
                    navController = navController,
                    viewModel = menuViewModel,
                    idMesa = idMesa
                )
        }


        composable("mesa") {
            MesaScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable("cliente") {
            ClienteScreen(
                navController = navController,
                viewModel = menuViewModel,
            )
        }

        composable("produto/{Id}/{qt}",
            arguments = listOf(
                navArgument(name = "Id"){
                    type = NavType.IntType
                },
                navArgument(name = "qt") {
                    type = NavType.IntType
                }
            )
        ) {id-> val idMesa = id.arguments?.getInt("Id")
            val qt = id.arguments?.getInt("qt")
            ProdutoScreen(
                navController = navController,
                modifier = Modifier,
                viewModel = menuViewModel,
                idmesa = idMesa,
                qtConvit = qt
            )
        }
    }


}

fun closeAllScreens(navController: NavHostController) {
    navController.popBackStack(navController.graph.startDestinationId, false)
}
