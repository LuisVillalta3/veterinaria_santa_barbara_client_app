@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GreenPrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily
import androidx.compose.runtime.getValue
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.LoadingDialog
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.data.LoginData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.data.LoginErrorsData

@Composable
fun loginView(navControler: NavHostController, viewModel: LoginViewModel = viewModel()) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        val configuration = LocalConfiguration.current
        val screenHeight = configuration.screenHeightDp
        val loginData by viewModel.loginData.collectAsState()
        val loginErrors by viewModel.loginErrors.collectAsState()
        val isLoading by viewModel.isLoading.collectAsState()

        LoadingDialog(isLoading)

        veterinarioImage(screenHeight)
        formContainer(screenHeight, loginData, viewModel, navControler, loginErrors)
    }
}

@Composable
fun veterinarioImage(screenHeight: Int) {
    Image(
        painter = painterResource(id = R.drawable.vet_pet_img),
        contentDescription = "Pet doctor",
        modifier = Modifier
            .fillMaxWidth()
            .height((screenHeight.toDouble() * .45).dp)
    )
}

@Composable
fun formContainer(
    screenHeight: Int,
    loginData: LoginData,
    viewModel: LoginViewModel,
    navControler: NavHostController,
    loginErrors: LoginErrorsData
) {
    Column(
        modifier = Modifier
            .background(BluePrimary)
            .padding(25.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .defaultMinSize((screenHeight.toDouble() * .55).dp)
    ) {
        titleContainer()
        SpacerUi(height = 20.dp)
        emailInput(loginData, viewModel, loginErrors)
        SpacerUi(height = 10.dp)
        passwordInput(loginData, viewModel, loginErrors)
        SpacerUi(height = 2.dp)
        olvidoPasswordBtn()
        SpacerUi(height = 13.dp)
        loginBtn(viewModel, navControler)
        SpacerUi(height = 5.dp)
        registrarseBtn(navControler, viewModel)
    }
}

@Composable
fun titleContainer() {
    Text(
        text = "Clínica Veterinaria",
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 20.sp,
    )
    Text(
        text = "Santa Bárbara",
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.Bold,
        color = Color.White,
        fontSize = 30.sp,
    )
}

@Composable
fun emailInput(loginData: LoginData, viewModel: LoginViewModel, loginErrors: LoginErrorsData) {
    val onValueChange = fun(it: String) {
        viewModel.onValueChange(it, loginData.password)
        viewModel.resetEmailErrors()
    }
    OutlinedLoginInput(
        loginData.email,
        { onValueChange(it) },
        "Correo electronico",
        KeyboardType.Email,
        isError = loginErrors.emailError != null,
        errorMessage = loginErrors.emailError
    )
}

@Composable
fun passwordInput(loginData: LoginData, viewModel: LoginViewModel, loginErrors: LoginErrorsData) {
    val onValueChange = fun(it: String) {
        viewModel.onValueChange(loginData.email, it)
        viewModel.resetPasswordErrors()
    }
    OutlinedLoginInput(
        loginData.password,
        { onValueChange(it) }, "Contraseña", KeyboardType.Password,
        isError = loginErrors.passwordError != null,
        errorMessage = loginErrors.passwordError,
        isPassword = true
    )
}

@Composable
fun olvidoPasswordBtn() {
    TextButton(onClick = { /*TODO*/ }, modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "¿Olvidó su contraseña?", fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.End,
            color = Color.White
        )
    }
}

@Composable
fun loginBtn(viewModel: LoginViewModel, navController: NavHostController) {
    Button(
        onClick = { viewModel.loginAction(navController) },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(7.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = GreenPrimary
        )
    ) {
        Text(
            text = "Iniciar Sesión", fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            color = Color.White
        )
    }
}

@Composable
fun registrarseBtn(navControler: NavHostController, viewModel: LoginViewModel) {
    Button(
        onClick = { viewModel.goToRegister(navControler) }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(7.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
        )
    ) {
        Text(
            text = "Registrarse", fontFamily = poppinsFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp),
            textAlign = TextAlign.Center,
            color = BluePrimary
        )
    }
}
