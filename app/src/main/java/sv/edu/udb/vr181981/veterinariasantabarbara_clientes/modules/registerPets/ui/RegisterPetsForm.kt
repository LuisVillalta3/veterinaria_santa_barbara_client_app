package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.CustomDatePickerField
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.IconRadioGroup
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.MyCheckbox
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.SpacerUi
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.ui.OutlinedRegisterInput

@Composable
fun RegisterPetForm(
    viewModel: IRegisterPetsFormViewModel
) {
    val formData by viewModel.formData.collectAsState()
    val errors by viewModel.formErrors.collectAsState()
    val listAnimalTypes = viewModel.listAnimalTypes
    val listGenders = viewModel.listGenders

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        NombreInput(
            value = formData.nombre,
            onValueChange = { viewModel.onChangePetName(it) },
            errorMessage = errors.nombreErrors
        )
        SpacerUi(height = 15.dp)
        IconRadioGroup(
            options = listAnimalTypes.map { it.drawableRedId to it.displayName },
            selectedOption = formData.especie,
            label = "Especie de su mascota",
            onChangeOption = { viewModel.onChangePetEspecie(it) }
        )
        if (formData.especie == "Pregunta") {
            SpacerUi(height = 15.dp)
            EspecieInput(
                value = formData.especieTxt ?: "",
                onValueChange = { viewModel.onChangePetEspecieTxt(it) },
                errorMessage = errors.especieTxtErrors
            )
        }
        SpacerUi(height = 15.dp)
        MyCheckbox(
            checked = formData.razaDesconocida,
            onCheckedChange = { viewModel.onChangePetRazaDesconocida(it) },
            label = "Raza desconocida"
        )
        if (!formData.razaDesconocida) {
            SpacerUi(height = 15.dp)
            RazaInput(
                value = formData.razaTxt ?: "",
                onValueChange = { viewModel.onChangePetRazaTxt(it) },
                errorMessage = errors.razaTxtErrors
            )
        }
        SpacerUi(height = 15.dp)
        IconRadioGroup(
            options = listGenders.map { it.drawableRedId to it.displayName },
            selectedOption = formData.generoMascota,
            label = "Genero de su mascota",
            horizontalArrangement = Arrangement.Start,
            onChangeOption = { viewModel.onChangeGenero(it) }
        )
        SpacerUi(height = 15.dp)
        CustomDatePickerField(
            label = "Fecha de nacimiento de su mascota",
            onChangeDate = { viewModel.onChangeDate(it) },
            selectedValue = ""
        )
    }
}

@Composable
fun NombreInput(value: String, onValueChange: (String) -> Unit, errorMessage: String? = null) {
    OutlinedRegisterInput(
        value = value,
        onValueChange = { onValueChange(it) },
        label = "Nombre de su mascota",
        keyboardType = KeyboardType.Text,
        errorMessage = errorMessage,
        isError = !errorMessage.isNullOrBlank()
    )
}

@Composable
fun EspecieInput(value: String, onValueChange: (String) -> Unit, errorMessage: String? = null) {
    OutlinedRegisterInput(
        value = value,
        onValueChange = { onValueChange(it) },
        label = "Especie de su mascota",
        keyboardType = KeyboardType.Text,
        errorMessage = errorMessage,
        isError = !errorMessage.isNullOrBlank()
    )
}

@Composable
fun RazaInput(value: String, onValueChange: (String) -> Unit, errorMessage: String? = null) {
    OutlinedRegisterInput(
        value = value,
        onValueChange = { onValueChange(it) },
        label = "Raza de su mascota",
        keyboardType = KeyboardType.Text,
        errorMessage = errorMessage,
        isError = !errorMessage.isNullOrBlank()
    )
}

