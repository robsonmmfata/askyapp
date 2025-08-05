package br.com.smartpos.asky.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.smartpos.asky.ui.theme.AskyTheme
import br.com.smartpos.asky.usecase.PreferencesUseCase
import br.com.smartpos.asky.viewModel.MenuViewModel
import br.com.smartpos.payxyz.navigation.NavGraph
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val menuViewModel: MenuViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackHandler {}
            AskyTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    menuViewModel = menuViewModel,
                )
            }
        }
    }
}
