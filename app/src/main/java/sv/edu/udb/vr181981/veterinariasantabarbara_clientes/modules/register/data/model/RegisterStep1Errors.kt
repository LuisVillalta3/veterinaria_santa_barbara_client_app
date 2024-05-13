package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model

data class RegisterStep1Errors(
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
)
