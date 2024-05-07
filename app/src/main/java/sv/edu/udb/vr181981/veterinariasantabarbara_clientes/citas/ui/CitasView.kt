package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.citas.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.layouts.AppLayout

@Composable
fun CitasView(navController: NavHostController) {
    AppLayout(
        navController = navController
    ) {
        Text(text = "Citas")
    }
}
