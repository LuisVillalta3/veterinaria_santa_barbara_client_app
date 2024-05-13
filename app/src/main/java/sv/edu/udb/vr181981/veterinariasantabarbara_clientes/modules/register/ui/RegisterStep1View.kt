package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.*
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterFormStep1Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.RegisterStep1Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.viewModels.RegisterStepOneViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.OutlinedRegisterInput
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun RegisterStep1View(
    navController: NavHostController,
    registerStepOneViewModel: RegisterStepOneViewModel = viewModel()
) {
    val registerFormData by registerStepOneViewModel.registerFormData.collectAsState()
    val registerStep1Errors by registerStepOneViewModel.registerStep1Errors.collectAsState()
    val isLoading by registerStepOneViewModel.isLoading.collectAsState()
    val showingBackDialog by registerStepOneViewModel.showingBackDialog.collectAsState()

    LoadingDialog(isLoading)

    BackHandler(enabled = !isLoading) {
        if (!isLoading) {
            registerStepOneViewModel.showConfirmDialog()
        }
    }

    ConfirmDialog(
        titleText = "¿Esta seguro?",
        descriptionText = "Su proceso de registro no ha terminado, si regresa al login tendra que empezar todo el proceso desde el inicio",
        cancelButtonText = "Regresar al login",
        confirmButtonText = "Continuar aquí",
        showConfirmDialog = showingBackDialog,
        onDismissRequest = { registerStepOneViewModel.removeTempUserFromCache(navController) },
        onConfirmAction = { registerStepOneViewModel.hideConfirmDialog() }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ImageContainer()
            Stepper(1, 4, "Registro de usuario", "Confirmar email")
            FormContainer(registerFormData, registerStep1Errors, registerStepOneViewModel)
        }
        SteppersButtons(
            backButtonText = "Cancelar",
            nextButtonText = "Siguiente",
            { registerStepOneViewModel.showConfirmDialog() },
            { registerStepOneViewModel.executeForm(navController) }
        )
    }
}

@Composable
fun ImageContainer() {
    Image(
        painter = painterResource(id = R.drawable.cv_santa_barbara),
        contentDescription = "Clinica Veterinaria Santa Barbara",
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 25.dp, horizontal = 25.dp)
            .size(65.dp),
    )
}

@Composable
private fun FormContainer(
    registerFormData: RegisterFormStep1Data,
    registerStep1Errors: RegisterStep1Errors,
    registerStepOneViewModel: RegisterStepOneViewModel
) {
    Column(
        modifier = Modifier.padding(25.dp)
    ) {
        Text(
            text = "Por favor, complete los siguientes campos con sus datos para iniciar sesión",
            color = GrayColor,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        SpacerUi(height = 20.dp)
        CorreoInput(registerFormData, registerStep1Errors, registerStepOneViewModel)
        SpacerUi(height = 15.dp)
        PasswordInput(registerFormData, registerStep1Errors, registerStepOneViewModel)
        SpacerUi(height = 15.dp)
        ConfirmPasswordInput(registerFormData, registerStep1Errors, registerStepOneViewModel)
        SpacerUi(height = 15.dp)
    }
}

@Composable
fun CorreoInput(
    registerFormData: RegisterFormStep1Data,
    registerStep1Errors: RegisterStep1Errors,
    registerStepOneViewModel: RegisterStepOneViewModel
) {
    OutlinedRegisterInput(
        value = registerFormData.email,
        onValueChange = { registerStepOneViewModel.onChangeEmail(it) },
        label = "Correo electronico",
        keyboardType = KeyboardType.Email,
        isError = !registerStep1Errors.emailError.isNullOrBlank(),
        errorMessage = registerStep1Errors.emailError
    )
}

@Composable
fun PasswordInput(
    registerFormData: RegisterFormStep1Data,
    registerStep1Errors: RegisterStep1Errors,
    registerStepOneViewModel: RegisterStepOneViewModel
) {
    OutlinedRegisterInput(
        value = registerFormData.password,
        onValueChange = { registerStepOneViewModel.onChangePassword(it) },
        label = "Contraseña",
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isError = !registerStep1Errors.passwordError.isNullOrBlank(),
        errorMessage = registerStep1Errors.passwordError
    )
}

@Composable
fun ConfirmPasswordInput(
    registerFormData: RegisterFormStep1Data,
    registerStep1Errors: RegisterStep1Errors,
    registerStepOneViewModel: RegisterStepOneViewModel
) {
    OutlinedRegisterInput(
        value = registerFormData.confirmPassword,
        onValueChange = { registerStepOneViewModel.onChangeConfirmPassword(it) },
        label = "Contraseña",
        keyboardType = KeyboardType.Password,
        isPassword = true,
        isError = !registerStep1Errors.passwordError.isNullOrBlank(),
        errorMessage = registerStep1Errors.passwordError
    )
}