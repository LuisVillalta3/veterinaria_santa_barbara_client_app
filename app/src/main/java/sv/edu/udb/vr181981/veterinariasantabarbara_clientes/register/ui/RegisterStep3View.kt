@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.DateTimePickerField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.LoadingDialog
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep3Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterStep2Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterStep3Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels.RegisterStepThreeViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels.RegisterStepTwoViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun RegisterStep3View(
    navController: NavHostController,
    rsViewModel: RegisterStepThreeViewModel = viewModel()
) {
    val formData by rsViewModel.registerFormData.collectAsState()
    val registerErrors by rsViewModel.registerStep3Errors.collectAsState()
    val isLoading by rsViewModel.isLoading.collectAsState()

    LoadingDialog(isLoading)

    BackHandler(enabled = !isLoading) {
        if (!isLoading) {
            rsViewModel.goBackAction(navController)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ImageContainer()
            Stepper(3, 4, "Información personal", "Mis mascotas")
            FormContainer(formData, registerErrors, rsViewModel)
        }
        SteppersButtons(
            backButtonText = "Anterior",
            nextButtonText = "Siguiente",
            { rsViewModel.goBackAction(navController) },
            { rsViewModel.executeForm(navController) }
        )
    }
}

@Composable
private fun FormContainer(
    formData: RegisterFormStep3Data,
    registerErrors: RegisterStep3Errors,
    rsViewModel: RegisterStepThreeViewModel
) {
    Column(
        modifier = Modifier.padding(25.dp)
    ) {
        Text(
            text = "Por favor, ingrese los siguientes datos personales para continuar con el registro",
            color = GrayColor,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )
        SpacerUi(height = 20.dp)
        NombreInput(formData, registerErrors, rsViewModel)
        SpacerUi(height = 15.dp)
        ApellidoInput(formData, registerErrors, rsViewModel)
        SpacerUi(height = 15.dp)
        DateTimePickerField(
            label = "Fecha de nacimiento",
            onChangeDate = { rsViewModel.onChangeFechaNacimiento(it) },
            selectedValue = formData.fechaNacimiento
        )
        SpacerUi(height = 15.dp)
        TelefonoInput(formData, registerErrors, rsViewModel)
    }
}

@Composable
private fun NombreInput(
    formData: RegisterFormStep3Data,
    registerErrors: RegisterStep3Errors,
    rsViewModel: RegisterStepThreeViewModel
) {
    OutlinedRegisterInput(
        value = formData.nombre,
        onValueChange = { rsViewModel.onChangeNombre(it) },
        label = "Nombre",
        isError = !registerErrors.nombreError.isNullOrBlank(),
        errorMessage = registerErrors.nombreError
    )
}

@Composable
private fun ApellidoInput(
    formData: RegisterFormStep3Data,
    registerErrors: RegisterStep3Errors,
    rsViewModel: RegisterStepThreeViewModel
) {
    OutlinedRegisterInput(
        value = formData.apellido,
        onValueChange = { rsViewModel.onChangeApellido(it) },
        label = "Apellido",
        isError = !registerErrors.apellidoError.isNullOrBlank(),
        errorMessage = registerErrors.apellidoError
    )
}

@Composable
private fun TelefonoInput(
    formData: RegisterFormStep3Data,
    registerErrors: RegisterStep3Errors,
    rsViewModel: RegisterStepThreeViewModel
) {
    OutlinedRegisterInput(
        value = formData.telefono,
        onValueChange = { rsViewModel.onChangeTelefono(it) },
        label = "Teléfono",
        keyboardType = KeyboardType.Phone,
        isError = !registerErrors.telefonoError.isNullOrBlank(),
        errorMessage = registerErrors.telefonoError
    )
}