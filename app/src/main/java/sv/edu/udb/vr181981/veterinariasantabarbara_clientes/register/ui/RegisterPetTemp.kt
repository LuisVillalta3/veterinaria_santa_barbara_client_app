package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels.RegisterPetTempViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.registerPets.ui.RegisterPetForm
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun RegisterPetTemp(navController: NavHostController, viewModel: RegisterPetTempViewModel = viewModel()) {
    BackHandler(enabled = true) {
        viewModel.goBackAction(navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column {
            PetImageContainer()
            RegisterPetForm(viewModel = viewModel)
        }
        SteppersButtons(
            backButtonText = "Cancelar",
            nextButtonText = "Agregar",
            backBtnAction = { viewModel.goBackAction(navController) },
            nextBtnAction = { viewModel.postForm(navController) }
        )
    }
}

@Composable
private fun PetImageContainer() {
    Image(
        painter = painterResource(id = R.drawable.cv_santa_barbara),
        contentDescription = "Clinica Veterinaria Santa Barbara",
        modifier = Modifier
            .background(color = BluePrimary)
            .fillMaxWidth()
            .padding(vertical = 25.dp, horizontal = 25.dp)
            .size(65.dp),
        colorFilter = ColorFilter.tint(color = Color.White)
    )
}
