package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.data.ErrorData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.data.ResponseData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.utils.Utils
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep3Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterStep3Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.UserTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.domain.RegisterStep3Controller

class RegisterStepThreeViewModel : ViewModel() {
    private val registerStep3Controller = RegisterStep3Controller()

    private val _registerFormData = MutableStateFlow(RegisterFormStep3Data())
    val registerFormData: StateFlow<RegisterFormStep3Data> = _registerFormData.asStateFlow()

    private val _registerStep3Errors = MutableStateFlow(RegisterStep3Errors())
    val registerStep3Errors: StateFlow<RegisterStep3Errors> = _registerStep3Errors.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _loading

    init {
        getTempUserData()
    }

    private fun getTempUserData() {
        val data = registerStep3Controller.getTempUserData()
        if (data != null) {
            _registerFormData.update { currentState ->
                currentState.copy(
                    nombre = data.nombre,
                    apellido = data.apellido,
                    telefono = data.telefono,
                    fechaNacimiento = data.fechaNacimiento
                )
            }
        }
    }

    fun onValueChange(nombre: String, apellido: String, fechaNacimiento: String, telefono: String) {
        _registerFormData.update { currentState ->
            currentState.copy(
                nombre = nombre,
                apellido = apellido,
                fechaNacimiento = fechaNacimiento,
                telefono = telefono,
            )
        }
    }

    private fun validateNombre(): Boolean {
        val errorMessage: String? =
            if (_registerFormData.value.nombre.isEmpty()) "Ingrese su nombre"
            else null

        _registerStep3Errors.update { currentState ->
            currentState.copy(
                nombreError = errorMessage
            )
        }

        return errorMessage.isNullOrBlank()
    }

    private fun validateApellido(): Boolean {
        val errorMessage: String? =
            if (_registerFormData.value.apellido.isEmpty()) "Ingrese su apellido"
            else null

        _registerStep3Errors.update { currentState ->
            currentState.copy(
                apellidoError = errorMessage
            )
        }

        return errorMessage.isNullOrBlank()
    }

    private fun validateFechaNacimiento(): Boolean {
        val errorMessage: String? =
            if (_registerFormData.value.fechaNacimiento.isEmpty()) "Ingrese fecha de nacimienta"
            else null

        _registerStep3Errors.update { currentState ->
            currentState.copy(
                fechaNacimientoError = errorMessage
            )
        }

        return errorMessage.isNullOrBlank()
    }

    private fun validateTelefono(): Boolean {
        val errorMessage: String? =
            if (_registerFormData.value.fechaNacimiento.isEmpty()) "Ingrese su número de teléfono"
            else null

        _registerStep3Errors.update { currentState ->
            currentState.copy(
                telefonoError = errorMessage
            )
        }

        return errorMessage.isNullOrBlank()
    }

    private fun isValidData(): Boolean =
        validateApellido() && validateNombre() && validateTelefono() && validateFechaNacimiento()

    private fun resetNombreErrors() {
        _registerStep3Errors.update { currentState ->
            currentState.copy(
                nombreError = null
            )
        }
    }

    private fun resetApellidoErrors() {
        _registerStep3Errors.update { currentState ->
            currentState.copy(
                apellidoError = null
            )
        }
    }

    private fun resetFechaNacimientoErrors() {
        _registerStep3Errors.update { currentState ->
            currentState.copy(
                fechaNacimientoError = null
            )
        }
    }

    private fun resetTelefonoErrors() {
        _registerStep3Errors.update { currentState ->
            currentState.copy(
                telefonoError = null
            )
        }
    }

    fun onChangeNombre(str: String) {
        resetNombreErrors()
        onValueChange(
            str,
            _registerFormData.value.apellido,
            _registerFormData.value.fechaNacimiento,
            _registerFormData.value.telefono
        )
    }

    fun onChangeApellido(str: String) {
        resetApellidoErrors()
        onValueChange(
            _registerFormData.value.nombre,
            str,
            _registerFormData.value.fechaNacimiento,
            _registerFormData.value.telefono
        )
    }

    fun onChangeFechaNacimiento(millis: Long) {
        resetFechaNacimientoErrors()
        onValueChange(
            _registerFormData.value.nombre,
            _registerFormData.value.apellido,
            Utils.transformMillisToDateString(millis),
            _registerFormData.value.telefono
        )
    }

    fun onChangeTelefono(str: String) {
        resetTelefonoErrors()
        onValueChange(
            _registerFormData.value.nombre,
            _registerFormData.value.apellido,
            _registerFormData.value.fechaNacimiento,
            str,
        )
    }

    private fun updateIsLoading(isLoading: Boolean) {
        _loading.update { isLoading }
    }

    fun executeForm(navController: NavHostController) {
        updateIsLoading(true)
        if (!isValidData()) {
            updateIsLoading(false)
            return
        }

        val response = addDataToUserTemp()

        if (response.isSuccess) {
            navController.navigate(Router.registerStep4Route)
        }
    }

    private fun addDataToUserTemp(): ResponseData<UserTempData> = try {
        runBlocking {
            return@runBlocking registerStep3Controller.addTempUserData(registerFormData.value)
        }
    } catch (ex: Exception) {
        ResponseData<UserTempData>(
            isSuccess = false,
            errorData = ErrorData(errorMessage = ex.message)
        )
    } finally {
        updateIsLoading(false)
    }

    fun goBackAction(navController: NavHostController) {
        navController.navigate(Router.registerStep1Route) {
            launchSingleTop = true
            popUpTo(Router.registerStep3Route) { inclusive = true }
        }
    }
}
