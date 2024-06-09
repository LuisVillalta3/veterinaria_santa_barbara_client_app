package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PoweredCitaEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.CitaRepository

class VerCitaViewModel() : ViewModel() {
    private val _citas = MutableStateFlow<PoweredCitaEntity?>(null)
    val cita = _citas.asStateFlow()

    fun getCita(citaId: String, navController: NavHostController) {
        if (citaId == "") {
            navController.popBackStack()
            return
        }
        viewModelScope.launch {
            val cita = CitaRepository().getCitaById(citaId)
            if (cita == null) {
                navController.popBackStack()
            } else {
                _citas.update { cita }
            }
        }
    }
}