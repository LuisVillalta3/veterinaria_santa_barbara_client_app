package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.citas.ui.CitasView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.SharedPreferencesManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.home.ui.HomeView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.ui.loginView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.notifications.ui.NotificationsView
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.RegisterPetTemp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.RegisterStep2View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.RegisterStep3View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.RegisterStep4View
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.RegisterStep1View

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
    }
}
