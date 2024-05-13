package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities

data class CitaEntity(
    val id: String,
    val doctorId: String,
    val fecha: String,
    val hora: String,
    val userId: String,
    val mascotaId: String,
    val tipoCita: String,
)
