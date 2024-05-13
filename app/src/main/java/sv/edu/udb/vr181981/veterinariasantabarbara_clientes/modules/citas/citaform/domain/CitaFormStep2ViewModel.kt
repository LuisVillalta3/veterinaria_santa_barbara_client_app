package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.components.ListItem
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.AnimalTypeEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.TipoConsultaEnum
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.DoctorRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.PetRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitaFormData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data.RegisterCitasRepository
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.navigation.Router
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.utils.Utils

class CitaFormStep2ViewModel : ViewModel() {
    private val _citaTempForm = MutableStateFlow(RegisterCitaFormData())
    val citaTempForm = _citaTempForm.asStateFlow()

    private val _formErrors = MutableStateFlow(RegisterCitaFormData())
    val formErrors = _formErrors.asStateFlow()

    private val _doctoresList = MutableStateFlow(emptyList<ListItem>())
    val doctoresList = _doctoresList.asStateFlow()

    val typeConsulta: List<TipoConsultaEnum> = TipoConsultaEnum.values().toList()

    init {
        setDefaultFormData()
        getDoctores()
    }

    private fun getDoctores() {
        viewModelScope.launch {
            val doctores = DoctorRepository().getDoctorsData()
            _doctoresList.update {
                doctores.map { doctor ->
                    ListItem(
                        id = doctor.id,
                        name = doctor.nombre
                    )
                }
            }
        }
    }

    private fun setDefaultFormData() {
        viewModelScope.launch {
            RegisterCitasRepository().getTempCitaData()?.let { tempCita ->
                _citaTempForm.update {
                    it.copy(
                        fecha = tempCita.fecha,
                        tipoConsulta = tempCita.tipoConsulta,
                        doctorId = tempCita.doctorId,
                        horaInicio = tempCita.hora["inicio"] ?: "",
                        horaFin = tempCita.hora["fin"] ?: ""
                    )
                }
            }
        }
    }

    fun goBackAction(navController: NavHostController) {
        navController.navigate(Router.citaStep1Route) {
            launchSingleTop = true
            popUpTo(Router.citaStep2Route) { inclusive = true }
        }
    }

    fun onChangeTipoConsulta(txt: String) {
        _citaTempForm.update {
            it.copy(
                tipoConsulta = txt
            )
        }
    }

    fun onChangeDate(millis: Long) {
        _citaTempForm.update {
            it.copy(
                fecha = Utils.transformMillisToDateString(millis)
            )
        }
    }

    fun onChangeHoraInicio(text: String) {
        _citaTempForm.update {
            it.copy(
                horaInicio = text
            )
        }
    }

    fun onChangeHoraFin(text: String) {
        _citaTempForm.update {
            it.copy(
                horaFin = text
            )
        }
    }

    fun onChangeDoctor(doctorId: String) {
        _citaTempForm.update {
            it.copy(
                doctorId = doctorId,
                doctorName = _doctoresList.value.find { doctor -> doctor.id == doctorId }?.name ?: ""
            )
        }
    }

    fun continueCita(navController: NavHostController) {
        if (RegisterCitasRepository().addCitaData(citaTempForm.value)) {
            navController.navigate(Router.citaStep3Route)
        }
    }
}