package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.ui.CitasView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.SharedPreferencesManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui.CitaFormStep1View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui.CitaFormStep2View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui.CitaFormStep3View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.ui.VerCitaView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.home.ui.HomeView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.login.ui.loginView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.ui.NotificationsView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.RegisterPetTemp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.RegisterStep2View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.RegisterStep3View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.RegisterStep4View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.RegisterStep1View

@Composable
fun NavigationHost() {
    val navController = rememberNavController()
    val sharedPreferences = SharedPreferencesManager()
    val startDestination = if (sharedPreferences.isLoggedIn()) {
        Router.homeRoute
    } else {
        Router.loginRoute
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Router.loginRoute) {
            loginView(navController)
        }

        composable(Router.homeRoute) {
            HomeView(navController)
        }

        composable(Router.citasRoute) {
            CitasView(navController = navController)
        }

        composable(Router.registerStep1Route) {
            RegisterStep1View(navController)
        }

        composable(Router.registerStep2Route) {
            RegisterStep2View(navController)
        }

        composable(Router.registerStep3Route) {
            RegisterStep3View(navController)
        }

        composable(Router.registerStep4Route) {
            RegisterStep4View(navController)
        }

        composable(Router.registerPetTemp) {
            RegisterPetTemp(navController)
        }

        composable(Router.notificationsRoute) {
            NotificationsView(navController)
        }

        composable(Router.citaStep1Route) {
            CitaFormStep1View(navController)
        }

        composable(Router.citaStep2Route) {
            CitaFormStep2View(navController)
        }

        composable(Router.citaStep3Route) {
            CitaFormStep3View(navController)
        }

        composable(Router.verCitaDataRoute) { backStackEntry ->
            val citaId = backStackEntry.arguments?.getString("citaId") ?: ""
            VerCitaView(citaId, navController)
        }
    }
}
