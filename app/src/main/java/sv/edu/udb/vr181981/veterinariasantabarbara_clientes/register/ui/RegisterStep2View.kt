@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.LoadingDialog
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.OtpInputField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterFormStep2Data
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.RegisterStep2Errors
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels.RegisterStepTwoViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun RegisterStep2View(
    navController: NavHostController,
    rsViewModel: RegisterStepTwoViewModel = viewModel()
) {
    val formData by rsViewModel.registerFormData.collectAsState()
    val registerErrors by rsViewModel.registerStep2Errors.collectAsState()
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
            Stepper(2, 4, "Confirmar email", "Información personal")
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
    formData: RegisterFormStep2Data,
    registerErrors: RegisterStep2Errors,
    rsViewModel: RegisterStepTwoViewModel
) {
    Column(
        modifier = Modifier.padding(25.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_mail_24),
            contentDescription = "Mail icon",
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp),
        )
        Text(
            text = "Por favor, ingrese el código de confirmación que se le envio a su correo electrónico ",
            color = GrayColor,
            textAlign = TextAlign.Center,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp
        )

        val focusRequester = remember { FocusRequester() }
        var isOtpFilled by remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current

        OtpInputField(
            modifier = Modifier
                .padding(top = 48.dp)
                .focusRequester(focusRequester)
                .fillMaxWidth(),
            otpText = formData.verificationCode,
            shouldCursorBlink = false,
            otpLength = 5,
            onOtpModified = { value, otpFilled ->
                rsViewModel.onValueChange(value)
                isOtpFilled = otpFilled
                if (otpFilled) {
                    keyboardController?.hide()
                }
            }
        )

        if (!registerErrors.verificationCodeError.isNullOrBlank()) {
            Text(
                text = registerErrors.verificationCodeError,
                color = Color.Red,
                fontFamily = poppinsFamily,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
