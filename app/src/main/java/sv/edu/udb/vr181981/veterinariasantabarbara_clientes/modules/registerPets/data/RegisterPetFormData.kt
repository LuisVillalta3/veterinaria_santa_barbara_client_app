package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.registerPets.data

data class RegisterPetFormData(
    val nombre: String = "",
    val especie: String = "Perro",
    val especieTxt: String? = "",
    val razaDesconocida: Boolean = true,
    val razaTxt: String? = "",
    val generoMascota: String = "Man",
    val fechaNacimiento: String = ""
)
