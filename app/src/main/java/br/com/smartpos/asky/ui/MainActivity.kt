package br.com.smartpos.asky.ui

import android.os.Bundle
// Removido ComponentActivity pois AppCompatActivity já é usada
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
// Imports de UI do Compose não usados diretamente aqui, podem ser removidos se não forem
// import androidx.compose.foundation.layout.fillMaxSize
// import androidx.compose.foundation.layout.padding
// import androidx.compose.material3.Scaffold
// import androidx.compose.material3.Text
// import androidx.compose.runtime.Composable
// import androidx.compose.ui.Modifier
// import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import br.com.smartpos.asky.data.remote.api.AskyApi // Import adicionado
import br.com.smartpos.asky.ui.theme.AskyTheme
// import br.com.smartpos.asky.usecase.PreferencesUseCase // Não usado diretamente
import br.com.smartpos.asky.viewModel.MenuViewModel
import br.com.smartpos.asky.navigation.NavGraph
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val menuViewModel: MenuViewModel by viewModel()
    private val askyApi: AskyApi by inject() // AskyApi injetada

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackHandler {
                // Considere se você precisa de um BackHandler global vazio aqui
                // ou se a navegação lida com o botão voltar adequadamente.
            }
            AskyTheme {
                val navController = rememberNavController()
                NavGraph(
                    navController = navController,
                    menuViewModel = menuViewModel,
                    askyApi = askyApi // Passando a instância da API
                )
            }
        }
    }
}
