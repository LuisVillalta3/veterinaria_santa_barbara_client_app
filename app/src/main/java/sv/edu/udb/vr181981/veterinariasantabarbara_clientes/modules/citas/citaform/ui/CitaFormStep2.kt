package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.BackHandler
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.CustomDatePickerField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.DropdownField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.IconRadioGroup
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SearchableDropdown
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.Stepper
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SteppersButtons
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.TimePickerField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitaFormData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain.CitaFormStep2ViewModel
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.ui.ImageContainer
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.ui.theme.BluePrimary

@Composable
fun CitaFormStep2View(
    navController: NavHostController,
    viewModel: CitaFormStep2ViewModel = viewModel()
) {
    val formData by viewModel.citaTempForm.collectAsState()
    val registerErrors by viewModel.formErrors.collectAsState()

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
            Stepper(2, 3, "Datos de la cita", "Confirmar cita")
            FormContainer(formData, registerErrors, viewModel)
        }
        SteppersButtons(
            backButtonText = "Anterior",
            nextButtonText = "Siguiente",
            { viewModel.goBackAction(navController) },
            { viewModel.continueCita(navController) }
        )
    }
}

@Composable
fun FormContainer(
    formData: RegisterCitaFormData,
    registerErrors: RegisterCitaFormData,
    viewModel: CitaFormStep2ViewModel
) {
    val typeConsulta = viewModel.typeConsulta
    val doctoresList by viewModel.doctoresList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        SpacerUi(height = 20.dp)
        IconRadioGroup(
            options = typeConsulta.map { it.drawableRedId to it.displayName },
            selectedOption = formData.tipoConsulta,
            label = "Tipo de consulta",
            onChangeOption = { viewModel.onChangeTipoConsulta(it) }
        )

        SpacerUi(height = 20.dp)
        CustomDatePickerField(
            label = "Fecha de la cita",
            onChangeDate = { viewModel.onChangeDate(it) },
            selectedValue = formData.fecha
        )

        SpacerUi(height = 20.dp)
        TimePickerField(
            onChangeHora = { viewModel.onChangeHoraInicio(it) },
            label = "Hora de inicio",
            selectedValue = formData.horaInicio
        )

        SpacerUi(height = 20.dp)
        DropdownField(
            label = "Doctor",
            list = doctoresList,
            selectedOption = formData.doctorId,
            onChangeSelected = { viewModel.onChangeDoctor(it) }
        )
    }
}
