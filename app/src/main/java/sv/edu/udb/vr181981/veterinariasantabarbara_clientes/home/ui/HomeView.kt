package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.home.ui

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.SharedPreferencesManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router

@Composable
fun HomeView(navController: NavHostController) {
    Text(text = "Home View")

    val sharedPreferences: SharedPreferencesManager = SharedPreferencesManager()
    
    Button(onClick = {
        sharedPreferences.clearAll()
        runBlocking {
            RoomManager.db.userDao().deleteUserLogin()
            RoomManager.db.petsDao().deleteAll()
        }
        // DESTROY ALL THE NAVIGATION STACK AND PUT LOGIN ROUTE AS THE UNIQUE ROUTE IN THE STACK
        navController.navigate(Router.loginRoute) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
        }
    }) {
        Text(text = "Logout")
    }
}
