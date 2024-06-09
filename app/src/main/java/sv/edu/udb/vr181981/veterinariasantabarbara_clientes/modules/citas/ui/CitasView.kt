@file:OptIn(ExperimentalMaterial3Api::class)

package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.layouts.AppLayout
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.PetCitaCard
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitasRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.domain.CitasViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun CitasView(navController: NavHostController, citasViewModel: CitasViewModel = viewModel()) {
    val citas by citasViewModel.citas.collectAsState()

    AppLayout(
        navController = navController
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            item { CitaContainer(navController) }
            citas?.let { citasList ->
                items(citasList.size) {
                    val cita = citasList[it]
                    PetCitaCard(cita, onClick = {
                        navController.navigate("cita/ver/${cita.id}")
                    })
                    SpacerUi(height = 20.dp)
                }
            }
        }
    }
}

@Composable
private fun CitaContainer(navController: NavHostController) {
    //val state = rememberDatePickerState()

    Text(
        text = "Citas y",
        fontWeight = FontWeight.SemiBold,
        fontSize = 28.sp,
        lineHeight = 28.sp * 1.20f,
        color = BluePrimary
    )
    Text(
        text = "Consultas",
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 28.sp * 1.20f,
        color = BluePrimary
    )
    SpacerUi(height = 20.dp)
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
    ) {
        TextButton(
            onClick = {
                RegisterCitasRepository().removeTempCitaFromCache()
                navController.navigate(Router.citaStep1Route)
            },
        ) {
            Icon(
                imageVector = Icons.Filled.AddCircle,
                contentDescription = "Pedir cita",
                tint = BluePrimary
            )
            Text(
                text = "Pedir cita",
                color = BluePrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
    SpacerUi(height = 20.dp)
    /*DatePicker(
        state = state,
        showModeToggle = false,
        colors = DatePickerDefaults.colors(
            selectedDayContainerColor = BluePrimary,
            selectedYearContainerColor = BluePrimary
        ),
    )*/
}
