package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.register.data.model

import kotlinx.serialization.Serializable

@Serializable
data class TempPetData(
    val nombreMascota: String = "",
    val especieMascota: String = "",
    val especieMascotaText: String = "",
    val razaDesconocida: Boolean = true,
    val razaText: String = "",
    val generoMascotas: String = "",
    val fechaNacimiento: String = ""
)
