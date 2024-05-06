package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.UserTempData

class RegisterViewModel: ViewModel() {
    private val _userTempData = MutableStateFlow(UserTempData())
    val userTempDate: StateFlow<UserTempData> = _userTempData.asStateFlow()
}