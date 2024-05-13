package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.viewModels

import androidx.navigation.NavHostController
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain.RegisterStep4Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.ui.IRegisterPetsFormViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router

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