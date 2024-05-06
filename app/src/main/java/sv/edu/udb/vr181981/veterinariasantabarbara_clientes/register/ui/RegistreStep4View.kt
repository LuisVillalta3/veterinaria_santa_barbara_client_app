package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.R
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.models.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.LoadingDialog
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.global.ui.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model.TempPetData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.viewModels.RegisterStepFourViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.GrayColor
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun RegisterStep4View(
    navController: NavHostController,
    rsViewModel: RegisterStepFourViewModel = viewModel()
) {
    val petsList by rsViewModel.petsList.collectAsState()
    val isLoading by rsViewModel.isLoading.collectAsState()

    LoadingDialog(isLoading)

    BackHandler(enabled = !isLoading) {
        if (!isLoading) rsViewModel.goBackAction(navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            ImageContainer()
            Stepper(4, 4, "Mis mascotas")
            AddPetButton(navController)

            if (petsList.isEmpty()) {
                NoPets()
            } else {
                MascotasList(petsList, rsViewModel)
            }
        }
        SteppersButtons(
            backButtonText = "Anterior",
            nextButtonText = "Registrarme",
            { rsViewModel.goBackAction(navController) },
            { rsViewModel.goCreateAccount(navController) }
        )
    }
}

@Composable
private fun AddPetButton(navController: NavHostController) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth()
            .clickable { navController.navigate(Router.registerPetTemp) }) {
        Image(
            painter = painterResource(id = R.drawable.baseline_add_circle_24),
            contentDescription = "Add pet"
        )
        Text(
            text = "Agregar mascota",
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            color = BluePrimary,
            modifier = Modifier.padding(start = 10.dp)
        )
    }
}

@Composable
private fun NoPets() {
    SpacerUi(height = 20.dp)
    Image(
        painter = painterResource(id = R.drawable.catdog),
        contentDescription = "Cat - dog image",
        modifier = Modifier
            .fillMaxWidth()
            .size(150.dp)
    )
    Text(
        text = "No ha registrado ninguna mascota", color = GrayColor,
        textAlign = TextAlign.Center,
        fontFamily = poppinsFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun MascotasList(petsList: MutableList<TempPetData>, rsViewModel: RegisterStepFourViewModel) {
    // Add a lazy column with the list of pets
    LazyColumn(
        modifier = Modifier.padding(horizontal = 25.dp)
    ) {
        items(petsList.size) { index ->
            val pet = petsList[index]
            PetItem(pet, rsViewModel)
        }
    }
}

@Composable
private fun PetItem(pet: TempPetData, rsViewModel: RegisterStepFourViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFEBEFFB))
            .padding(15.dp)
            .border(
                border = BorderStroke(0.dp, Color.Transparent),
                shape = RoundedCornerShape(20.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = AnimalTypeEnum.getDrawableRedIdByName(pet.especieMascota)!!),
            contentDescription = "Pet image",
            modifier = Modifier.size(50.dp)
        )
        Text(
            text = pet.nombreMascota,
            fontFamily = poppinsFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            color = BluePrimary,
            modifier = Modifier.fillMaxWidth(0.6f),
            textAlign = TextAlign.Start,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Row {
            IconButton(onClick = { rsViewModel.removeTempPetData(pet) }) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete button",
                    tint = Color.Red,
                    modifier = Modifier.size(25.dp)
                )
            }
        }
    }
    SpacerUi()
}

