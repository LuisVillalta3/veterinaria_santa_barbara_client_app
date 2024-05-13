package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.viewModels

import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.MyApp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep1Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterStep1Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain.RegisterStep1Controller
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router

class RegisterStepOneViewModel : ViewModel() {
    private val registerStep1Controller = RegisterStep1Controller()

    private val _registerFormData = MutableStateFlow(RegisterFormStep1Data())
    val registerFormData: StateFlow<RegisterFormStep1Data> = _registerFormData.asStateFlow()

    private val _registerStep1Errors = MutableStateFlow(RegisterStep1Errors())
    val registerStep1Errors: StateFlow<RegisterStep1Errors> = _registerStep1Errors.asStateFlow()

    private val _isVerified = MutableStateFlow(false)
    private val isVerified: StateFlow<Boolean>
        get() = _isVerified

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _loading

    private val _showingBackDialog = MutableStateFlow(false)
    val showingBackDialog: StateFlow<Boolean>
        get() = _showingBackDialog

    init {
        getTempUserData()
    }

    private fun getTempUserData() {
        val data = registerStep1Controller.getTempUserData()
        if (data != null) {
            _registerFormData.update { currentState ->
                currentState.copy(
                    email = data.email,
                    password = data.password,
                    confirmPassword = data.password
                )
            }

            _isVerified.update { data.isVerified }
        }
    }

    private fun onValueChange(email: String, password: String, confirmPassword: String) {
        _registerFormData.update { currentState ->
            currentState.copy(
                email = email,
                password = password,
                confirmPassword = confirmPassword
            )
        }
    }

    private fun validateEmail(): Boolean {
        val emailError: String? =
            if (_registerFormData.value.email.isEmpty()) "Ingrese su correo electrónico"
            else if (!Patterns.EMAIL_ADDRESS.matcher(_registerFormData.value.email)
                    .matches()
            ) "Ingrese un correo electrónico valido"
            else null

        _registerStep1Errors.update { currentState ->
            currentState.copy(
                emailError = emailError
            )
        }

        return emailError.isNullOrBlank()
    }

    private fun validatePassword(): Boolean {
        val passwordError: String? =
            if (_registerFormData.value.password.isEmpty()) "Ingrese su contraseña"
            else if (_registerFormData.value.password.length < 6) "La contraseña debe tener al menos 6 caracteres"
            else null

        _registerStep1Errors.update { currentState ->
            currentState.copy(
                passwordError = passwordError
            )
        }

        return passwordError.isNullOrBlank()
    }

    private fun validateConfirmPassword(): Boolean {
        val confirmPasswordError: String? =
            if (_registerFormData.value.confirmPassword.isEmpty()) "Ingrese su contraseña"
            else if (_registerFormData.value.password.length < 6) "La contraseña debe tener al menos 6 caracteres"
            else if (_registerFormData.value.password != _registerFormData.value.confirmPassword) "Las contraseñas no coincided"
            else null

        _registerStep1Errors.update { currentState ->
            currentState.copy(
                confirmPasswordError = confirmPasswordError
            )
        }

        return confirmPasswordError.isNullOrBlank()
    }

    private fun isValidData(): Boolean =
        validateEmail() && validatePassword() && validateConfirmPassword()

    private fun resetPasswordErrors() {
        _registerStep1Errors.update { currentState ->
            currentState.copy(
                passwordError = null
            )
        }
    }

    private fun resetConfirmPasswordErrors() {
        _registerStep1Errors.update { currentState ->
            currentState.copy(
                confirmPasswordError = null
            )
        }
    }

    private fun resetEmailErrors() {
        _registerStep1Errors.update { currentState ->
            currentState.copy(
                emailError = null
            )
        }
    }

    fun onChangeEmail(email: String) {
        resetEmailErrors()
        onValueChange(
            email,
            _registerFormData.value.password,
            _registerFormData.value.confirmPassword
        )
    }

    fun onChangePassword(password: String) {
        resetPasswordErrors()
        onValueChange(
            _registerFormData.value.email,
            password,
            _registerFormData.value.confirmPassword
        )
    }

    fun onChangeConfirmPassword(password: String) {
        resetConfirmPasswordErrors()
        onValueChange(_registerFormData.value.email, _registerFormData.value.password, password)
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

        if (createUserTemp()) {
            val route = if (isVerified.value) Router.registerStep3Route else Router.registerStep2Route
            navController.navigate(route)
        }
    }

    private fun createUserTemp(): Boolean = try {
        runBlocking {
            return@runBlocking registerStep1Controller.createTempUser(registerFormData.value)
        }
    } catch (_: Exception) {
        false
    } finally {
        updateIsLoading(false)
    }

    fun showConfirmDialog() {
        if (!isLoading.value) {
            _showingBackDialog.update { true }
        } else {
            Toast.makeText(
                MyApp.instance.applicationContext,
                "No puedes hacer esto en este momento",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun hideConfirmDialog() {
        _showingBackDialog.update { false }
    }

    fun removeTempUserFromCache(navController: NavHostController) {
        hideConfirmDialog()
        if (!isLoading.value) {
            registerStep1Controller.removeTempUserFromCache()
            navController.navigate(Router.loginRoute) {
                launchSingleTop = true
                popUpTo(Router.registerStep1Route) { inclusive = true }
            }
        } else {
            Toast.makeText(
                MyApp.instance.applicationContext,
                "No puedes volver atras mientras se esta ejecutando un proceso",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}