package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.enums.TipoConsultaEnum

data class RegisterCitaFormData(
    val fecha: String = "",
    val tipoConsulta: String = "Estilo",
    val doctorId: String = "",
    val doctorName: String = "",
    val horaInicio: String = "",
    val horaFin: String = "",
)
