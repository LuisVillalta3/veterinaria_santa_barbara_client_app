package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.login.ui

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.login.data.LoginData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.login.data.LoginErrorsData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.login.domain.LoginController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain.RegisterController
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val registerController = RegisterController()
    private val loginController = LoginController()

    private val _loginData = MutableStateFlow(LoginData())
    val loginData: StateFlow<LoginData> = _loginData.asStateFlow()

    private val _loginErrors = MutableStateFlow(LoginErrorsData())
    val loginErrors: StateFlow<LoginErrorsData> = _loginErrors.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
        get() = _loading

    fun onValueChange(email: String, password: String) {
        _loginData.update { currentState ->
            currentState.copy(
                email = email,
                password = password
            )
        }
    }

    private fun validateEmail(): Boolean {
        val emailError: String? =
            if (_loginData.value.email.isEmpty()) "Ingrese su correo electrónico"
            else if (!Patterns.EMAIL_ADDRESS.matcher(_loginData.value.email)
                    .matches()
            ) "Ingrese un correo electrónico valido"
            else null

        _loginErrors.update { currentState ->
            currentState.copy(
                emailError = emailError
            )
        }

        return emailError.isNullOrBlank()
    }

    private fun validatePassword(): Boolean {
        val passwordError: String? = if (_loginData.value.email.isEmpty()) "Ingrese su contraseña"
        else null

        _loginErrors.update { currentState ->
            currentState.copy(
                passwordError = passwordError
            )
        }

        return passwordError.isNullOrBlank()
    }

    private fun isValidForm(): Boolean = validateEmail() && validatePassword()

    fun resetPasswordErrors() {
        _loginErrors.update { currentState ->
            currentState.copy(
                passwordError = null
            )
        }
    }

    fun resetEmailErrors() {
        _loginErrors.update { currentState ->
            currentState.copy(
                emailError = null
            )
        }
    }

    fun loginAction(navController: NavHostController) {
        if (!isValidForm()) return
        _loading.update { true }

        viewModelScope.launch {
            val isLoginSuccess = loginController.loginUser(email = _loginData.value.email, password = _loginData.value.password)

            if (isLoginSuccess) {
                _loading.update { false }
                navController.navigate(Router.homeRoute) {
                    launchSingleTop = true
                    popUpTo(Router.loginRoute) { inclusive = true }
                }
            } else {
                _loginErrors.update { currentState ->
                    currentState.copy(
                        emailError = "Correo o contraseña incorrectos"
                    )
                }
                _loading.update { false }
            }
        }
    }

    fun goToRegister(navController: NavHostController) {
        registerController.removeTempUserFromCache()
        navController.navigate(Router.registerStep1Route)
    }
}
