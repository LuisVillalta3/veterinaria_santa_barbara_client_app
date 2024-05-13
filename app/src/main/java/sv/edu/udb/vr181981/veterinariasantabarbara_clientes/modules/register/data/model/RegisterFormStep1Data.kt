package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model

data class RegisterFormStep1Data(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false
)
