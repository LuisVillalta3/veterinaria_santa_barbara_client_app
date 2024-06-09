package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.domain

import android.util.Log
import androidx.compose.material3.rememberDatePickerState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PoweredCitaEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.CitaRepository

class CitasViewModel : ViewModel() {

    private val _citas = MutableStateFlow<MutableList<PoweredCitaEntity>?>(mutableListOf())
    val citas = _citas.asStateFlow()

    init {
        getCitas()
    }

    private fun getCitas() {
        viewModelScope.launch {
            val citas = CitaRepository().getCitas()
            Log.d("HomeViewModel", "Citas: $citas")
            _citas.update { citas.toMutableList() }
        }
    }
}