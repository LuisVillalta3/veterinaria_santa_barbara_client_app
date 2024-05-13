package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
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
import androidx.compose.ui.unit.*
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain.CitaFormStep1ViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.poppinsFamily

@Composable
fun CitaFormStep1View(
    navController: NavHostController,
    viewModel: CitaFormStep1ViewModel = viewModel()
) {
    val petsList by viewModel.mascotasList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = "Crear cita",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = BluePrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            )
            Stepper(1, 3, "Seleccionar mascota", "Datos de la cita")

            if (petsList.isNotEmpty()) {
                SpacerUi()
                MascotasList(petsList, viewModel, navController)
            } else {
                SpacerUi(height = 50.dp)
                CircularProgressIndicator(
                    color = BluePrimary,
                    modifier = Modifier
                        .padding(20.dp)
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally),
                    strokeWidth = 8.dp
                )
            }
        }
    }
}

@Composable
private fun MascotasList(
    petsList: MutableList<PetEntity>,
    viewModel: CitaFormStep1ViewModel,
    navController: NavHostController
) {
    // Add a lazy column with the list of pets
    LazyColumn(
        modifier = Modifier.padding(horizontal = 25.dp)
    ) {
        items(petsList.size) { index ->
            val pet = petsList[index]
            PetItem(pet, viewModel, navController)
        }
    }
}

@Composable
private fun PetItem(
    pet: PetEntity,
    viewModel: CitaFormStep1ViewModel,
    navController: NavHostController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFEBEFFB), shape = RoundedCornerShape(20.dp))
            .padding(15.dp)
            .border(
                border = BorderStroke(0.dp, Color.Transparent),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { viewModel.setMascota(navController, pet.id) },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = AnimalTypeEnum.getDrawableRedIdByName(pet.especie)!!),
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
    }
    SpacerUi()
}