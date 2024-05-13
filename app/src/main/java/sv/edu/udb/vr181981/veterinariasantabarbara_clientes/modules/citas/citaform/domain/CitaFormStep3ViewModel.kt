package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.ListItem
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.TipoConsultaEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.DoctorRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.PetRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.CitaTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitaFormData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitasRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.utils.Utils

class CitaFormStep3ViewModel : ViewModel() {
    private val _citaTempData = MutableStateFlow(CitaTempData())
    val citaTempData = _citaTempData.asStateFlow()

    init {
        getDefaultFormData()
    }

    private fun getDefaultFormData() {
        viewModelScope.launch {
            RegisterCitasRepository().getTempCitaData()?.let { tempCita ->
                _citaTempData.update { tempCita }
            }
        }
    }

    fun goBackAction(navController: NavHostController) {
        navController.navigate(Router.citaStep2Route) {
            launchSingleTop = true
            popUpTo(Router.citaStep3Route) { inclusive = true }
        }
    }

    fun createCita(navController: NavHostController) {
        viewModelScope.launch {
            if (RegisterCitasRepository().registerCita()) {
                navController.navigate(Router.citasRoute) {
                    launchSingleTop = true
                    popUpTo(Router.citaStep1Route) { inclusive = true }
                }
            }
        }
    }
}