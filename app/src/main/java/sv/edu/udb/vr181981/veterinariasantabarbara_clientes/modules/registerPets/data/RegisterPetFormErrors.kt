package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.data

data class RegisterPetFormErrors(
    val nombreErrors: String? = null,
    val especieErrors: String? = null,
    val especieTxtErrors: String? = null,
    val razaDesconocidaErrors: String? = null,
    val razaTxtErrors: String? = null,
    val generoMascotaErrors: String? = null,
    val fechaNacimientoErrors: String? = null
)
