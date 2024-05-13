package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.*
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.data.*
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.utils.Utils

abstract class IRegisterPetsFormViewModel : ViewModel() {
    private val _formData = MutableStateFlow(RegisterPetFormData())
    val formData = _formData.asStateFlow()

    private val _formErrors = MutableStateFlow(RegisterPetFormErrors())
    val formErrors = _formErrors.asStateFlow()

    val listAnimalTypes: List<AnimalTypeEnum> = AnimalTypeEnum.values().toList()

    val listGenders: List<GenderEnum> = GenderEnum.values().toList()

    abstract fun postForm(navController: NavHostController)

    fun onChangePetName(txt: String) {
        resetNombreErrors()
        _formData.update {
            it.copy(
                nombre = txt
            )
        }
    }

    fun onChangePetEspecie(txt: String) {
        resetEspecieErrors()
        _formData.update {
            it.copy(
                especie = txt,
                especieTxt = ""
            )
        }
    }

    fun onChangePetEspecieTxt(txt: String) {
        resetEspecieTxtErrors()
        _formData.update {
            it.copy(
                especieTxt = txt
            )
        }
    }

    fun onChangePetRazaDesconocida(v: Boolean) {
        resetRazaDesconocidaErrors()
        _formData.update {
            it.copy(
                razaDesconocida = v,
                razaTxt = ""
            )
        }
    }

    fun onChangePetRazaTxt(txt: String) {
        resetRazaTxtErrors()
        _formData.update {
            it.copy(
                razaTxt = txt
            )
        }
    }

    fun onChangeGenero(txt: String) {
        resetGeneroMascotaErrors()
        _formData.update {
            it.copy(
                generoMascota = txt
            )
        }
    }

    fun onChangeDate(millis: Long) {
        resetFechaNacimientoErrors()
        _formData.update {
            it.copy(
                fechaNacimiento = Utils.transformMillisToDateString(millis)
            )
        }
    }

    private fun validateNombre(): Boolean {
        val error: String? =
            if (_formData.value.nombre.isEmpty()) "Ingrese el nombre de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                nombreErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validateEspecie(): Boolean {
        val error: String? =
            if (_formData.value.especie.isEmpty()) "Seleccione la especie de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                especieErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validateEspecieTxt(): Boolean {
        if (_formData.value.especie != "Pregunta") return true

        val error: String? =
            if (_formData.value.especieTxt.isNullOrBlank()) "Ingrese la especie de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                especieTxtErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validateRazaDesconocida(): Boolean {
        val error: String? =
            if (!_formData.value.razaDesconocida && _formData.value.razaTxt.isNullOrBlank()) "Ingrese la raza de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                razaDesconocidaErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun validateRazaTxt(): Boolean {
        if (_formData.value.razaDesconocida) return true
        val error: String? =
            if (_formData.value.razaDesconocida && _formData.value.razaTxt.isNullOrBlank()) "Ingrese la raza de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                razaTxtErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    private fun fechaNacimiento(): Boolean {
        val error: String? =
            if (_formData.value.fechaNacimiento.isEmpty()) "Seleccione la fecha de nacimiento de su mascota"
            else null

        _formErrors.update { currentState ->
            currentState.copy(
                fechaNacimientoErrors = error
            )
        }

        return error.isNullOrBlank()
    }

    fun isValidData(): Boolean =
        validateNombre() && validateEspecie() && validateEspecieTxt() && validateRazaDesconocida() && validateRazaTxt() && fechaNacimiento()

    private fun resetNombreErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                nombreErrors = null
            )
        }
    }

    private fun resetEspecieErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                especieErrors = null
            )
        }
    }

    private fun resetEspecieTxtErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                especieTxtErrors = null
            )
        }
    }

    private fun resetRazaDesconocidaErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                razaDesconocidaErrors = null
            )
        }
    }

    private fun resetRazaTxtErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                razaTxtErrors = null
            )
        }
    }

    private fun resetGeneroMascotaErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                generoMascotaErrors = null
            )
        }
    }

    private fun resetFechaNacimientoErrors() {
        _formErrors.update { currentState ->
            currentState.copy(
                fechaNacimientoErrors = null
            )
        }
    }

    protected fun resetAllErrors() {
        resetNombreErrors()
        resetEspecieErrors()
        resetEspecieTxtErrors()
        resetRazaDesconocidaErrors()
        resetRazaTxtErrors()
        resetGeneroMascotaErrors()
        resetFechaNacimientoErrors()
    }
}
