package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.responses

data class ResponseData<T>(
    val isSuccess: Boolean = false,
    val data: T? = null,
    var errorData: ErrorData? = null
)
