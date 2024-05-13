package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain.CitaFormStep3ViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary
import androidx.lifecycle.viewmodel.compose.viewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.TipoConsultaEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.CitaTempData

@Composable
fun CitaFormStep3View(
    navController: NavHostController,
    viewModel: CitaFormStep3ViewModel = viewModel()
) {
    BackHandler(enabled = true) {
        viewModel.goBackAction(navController)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
            Stepper(3, 3, "Confirmar cita")
            DataContainer(viewModel)
        }
        SteppersButtons(
            backButtonText = "Anterior",
            nextButtonText = "Crear cita",
            { viewModel.goBackAction(navController) },
            { viewModel.createCita(navController) }
        )
    }
}

@Composable
private fun DataContainer(viewModel: CitaFormStep3ViewModel) {
    val citaTempData by viewModel.citaTempData.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        Text(
            text = "Mascota",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = AnimalTypeEnum.getDrawableRedIdByName(citaTempData.especie)!!),
                contentDescription = citaTempData.especie,
                modifier = Modifier.size(70.dp),
                colorFilter = ColorFilter.tint(color = BluePrimary)
            )
            Text(
                text = citaTempData.mascota,
                color = BluePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp
            )
        }

        SpacerUi(height = 15.dp)
        Text(
            text = "Tipo de consulta",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = citaTempData.tipoConsulta,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        SpacerUi(height = 15.dp)
        Text(
            text = "Doctor",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = citaTempData.doctor,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        SpacerUi(height = 15.dp)
        Text(
            text = "Fecha",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        Text(
            text = citaTempData.fecha,
            color = BluePrimary,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        SpacerUi(height = 15.dp)
        Text(
            text = "Hora",
            color = BluePrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        citaTempData.hora["inicio"]?.let {
            Text(
                text = it,
                color = BluePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }
}
