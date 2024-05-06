package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "lastname") val lastname: String,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "fechaNacimiento") val fechaNacimiento: String,
    @ColumnInfo(name = "telefono") val telefono: String,
    )
