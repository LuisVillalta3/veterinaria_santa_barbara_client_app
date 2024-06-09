package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.domain.VerCitaViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun VerCitaView(
    citaId: String,
    navController: NavHostController,
    viewModel: VerCitaViewModel = viewModel()
) {
    val citaData by viewModel.cita.collectAsState()

    if (citaData == null) {
        viewModel.getCita(citaId = navController.previousBackStackEntry?.arguments?.getString("citaId") ?: "", navController = navController)
        return
    }

    citaData?.let {
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
                    painter = painterResource(id = AnimalTypeEnum.getDrawableRedIdByName(citaData!!.mascota.especie)!!),
                    contentDescription = citaData!!.mascota.especie,
                    modifier = Modifier.size(70.dp),
                    colorFilter = ColorFilter.tint(color = BluePrimary)
                )
                Text(
                    text = citaData!!.mascota.nombreMascota,
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
                text = citaData!!.tipoCita,
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
                text = citaData!!.doctor.nombre,
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
                text = citaData!!.fecha,
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
        }
    }
}