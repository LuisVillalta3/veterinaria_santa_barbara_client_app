package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels

import androidx.navigation.NavHostController
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain.RegisterStep4Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.registerPets.ui.IRegisterPetsFormViewModel

class RegisterPetTempViewModel : IRegisterPetsFormViewModel() {
    private val registerStep4Controller = RegisterStep4Controller()

    override fun postForm(navController: NavHostController) {
        resetAllErrors()
        if (!isValidData()) return
        runBlocking {
            registerStep4Controller.addTempPetData(formData.value)
        }
        goBackAction(navController)
    }

    fun goBackAction(navController: NavHostController) {
        navController.navigate(Router.registerStep4Route) {
            launchSingleTop = true
            popUpTo(Router.registerPetTemp) { inclusive = true }
            popUpTo(Router.registerStep4Route) { inclusive = true }
        }
    }
}