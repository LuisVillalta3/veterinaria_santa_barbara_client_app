package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UserTempData(
    val id: Int? = null,
    val email: String = "",
    val password: String = "",
    val isSuccess: Boolean = false,
    val isVerified: Boolean = false,
    val nombre: String = "",
    val apellido: String = "",
    val fechaNacimiento: String = "",
    val telefono: String = "",
    val mascotas: MutableList<TempPetData> = mutableListOf<TempPetData>()
)
