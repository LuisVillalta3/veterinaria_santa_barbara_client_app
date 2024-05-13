package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses.*
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterStep2Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain.RegisterStep2Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router

class RegisterStepTwoViewModel : ViewModel() {
    private val registerStep2Controller = RegisterStep2Controller()

    private val _registerFormData = MutableStateFlow(RegisterFormStep2Data())
    val registerFormData: StateFlow<RegisterFormStep2Data> = _registerFormData.asStateFlow()

    private val _registerStep2Errors = MutableStateFlow(RegisterStep2Errors())
    val registerStep2Errors: StateFlow<RegisterStep2Errors> = _registerStep2Errors.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _loading

    fun onValueChange(verificationCode: String) {
        resetVerificationCodeErrors()
        _registerFormData.update { currentState ->
            currentState.copy(
                verificationCode = verificationCode
            )
        }
    }

    private fun validateVerificationCode(): Boolean {
        val verificationCode = _registerFormData.value.verificationCode
        val verificationCodeError: String? =
            if (verificationCode.isEmpty()) "Ingrese el código de verificación"
            else if (!verificationCode.all { it.isDigit() }) "Ingrese un código de verificación valido"
            else null

        _registerStep2Errors.update { currentState ->
            currentState.copy(
                verificationCodeError = verificationCodeError
            )
        }

        return verificationCodeError.isNullOrBlank()
    }

    private fun isValidData(): Boolean = validateVerificationCode()

    private fun resetVerificationCodeErrors() {
        _registerStep2Errors.update { currentState ->
            currentState.copy(
                verificationCodeError = null
            )
        }
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

        val response = verifyUserTemp()

        if (response.isSuccess) {
            navController.navigate(Router.registerStep3Route) {
                launchSingleTop = true
                popUpTo(Router.registerStep2Route) { inclusive = true }
            }
        } else {
            _registerStep2Errors.update { currentState ->
                currentState.copy(
                    verificationCodeError = response.errorData?.errorMessage
                )
            }
        }
    }

    private fun verifyUserTemp(): ResponseData<UserTempData> = try {
        runBlocking {
            return@runBlocking registerStep2Controller.verifyTempUser(registerFormData.value)
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
            popUpTo(Router.registerStep2Route) { inclusive = true }
        }
    }
}