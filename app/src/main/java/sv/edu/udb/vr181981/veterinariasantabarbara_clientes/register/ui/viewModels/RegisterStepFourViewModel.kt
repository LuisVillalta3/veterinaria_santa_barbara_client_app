package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.MyApp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.data.ErrorData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.data.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep1Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterStep2Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.TempPetData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.UserTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain.RegisterStep1Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain.RegisterStep2Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain.RegisterStep4Controller

class RegisterStepFourViewModel : ViewModel() {
    private val registerStep4Controller = RegisterStep4Controller()

    private val _petsList = MutableStateFlow(mutableListOf<TempPetData>())
    val petsList: StateFlow<MutableList<TempPetData>> = _petsList.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _loading

    init {
        getTempPetsList()
    }

    private fun getTempPetsList() {
        val data = registerStep4Controller.getTempUserData()
        data?.mascotas?.forEach {
            _petsList.value.add(it)
        }
    }

    fun goBackAction(navController: NavHostController) {
        navController.navigate(Router.registerStep3Route) {
            launchSingleTop = true
            popUpTo(Router.registerStep4Route) { inclusive = true }
        }
    }

    fun removeTempPetData(petData: TempPetData) {
        _petsList.update { currentList ->
            val newList = currentList.toMutableList()
            newList.remove(petData)
            newList
        }
        registerStep4Controller.removeTempPetData(petData)
    }

    fun goCreateAccount(navController: NavHostController) {
        _loading.update { true }
        if (_petsList.value.isEmpty()) {
            Toast.makeText(
                MyApp.instance.applicationContext,
                "Debe agregar al menos una mascota",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        viewModelScope.launch {
            val res = registerStep4Controller.registerUser()
            _loading.update { false }

            if (!res) {
                Toast.makeText(
                    MyApp.instance.applicationContext,
                    "Error al registrar usuario",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                navController.navigate(Router.homeRoute) {
                    launchSingleTop = true
                    popUpTo(Router.registerStep4Route) { inclusive = true }
                }
            }
        }
    }
}
