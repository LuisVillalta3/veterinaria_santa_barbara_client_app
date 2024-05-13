package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.home.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PoweredCitaEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.CitaRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.UserRepository

class HomeViewModel : ViewModel() {
    private val _userEntity = MutableStateFlow<UserEntity?>(null)
    val userEntity = _userEntity.asStateFlow()

    private val _citas = MutableStateFlow<MutableList<PoweredCitaEntity>?>(mutableListOf())
    val citas = _citas.asStateFlow()

    init {
        getUserData()
        getCitas()
    }

    private fun getUserData() {
        viewModelScope.launch {
            val ue = UserRepository().getUserData()
            Log.d("HomeViewModel", "UserEntity: $ue")
            _userEntity.update { ue }
        }
    }

    private fun getCitas() {
        viewModelScope.launch {
            val citas = CitaRepository().getCitas()
            Log.d("HomeViewModel", "Citas: $citas")
            _citas.update { citas.toMutableList() }
        }
    }
}
