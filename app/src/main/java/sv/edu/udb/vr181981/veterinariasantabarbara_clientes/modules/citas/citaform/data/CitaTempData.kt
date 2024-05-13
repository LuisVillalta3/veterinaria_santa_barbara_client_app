package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.citas.citaform.data

import kotlinx.serialization.Serializable

@Serializable
data class CitaTempData(
    val mascotaId: String = "",
    val mascota: String = "",
    val especie: String = "",
    val fecha: String = "",
    val hora: Map<String, String> = mapOf(),
    val tipoConsulta: String = "",
    val doctorId: String = "",
    val doctor: String = "",
)
