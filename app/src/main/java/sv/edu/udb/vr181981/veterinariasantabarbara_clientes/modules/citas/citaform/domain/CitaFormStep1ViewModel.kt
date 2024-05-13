package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.PetRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitasRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router

class CitaFormStep1ViewModel : ViewModel() {
    private val _mascotasList = MutableStateFlow(mutableListOf<PetEntity>())
    val mascotasList = _mascotasList.asStateFlow()

    init {
        getMascotasList()
    }

    private fun getMascotasList() {
        viewModelScope.launch {
            val list = PetRepository().getUserPets()
            _mascotasList.update { list.toMutableList() }
        }
    }

    fun setMascota(navController: NavHostController, mascotaId: String) {
        val pet = _mascotasList.value.find { it.id == mascotaId }
        pet?.let {
            viewModelScope.launch {
                if (RegisterCitasRepository().createTempCita(
                        mascotaId,
                        it.nombreMascota,
                        it.especie,
                    )
                ) {
                    navController.navigate(Router.citaStep2Route)
                }
            }
        }
    }

    fun goBackAction(navController: NavHostController) {
        RegisterCitasRepository().removeTempCitaFromCache()
        navController.navigate(Router.citasRoute) {
            launchSingleTop = true
            popUpTo(Router.citaStep1Route) { inclusive = true }
        }
    }
}