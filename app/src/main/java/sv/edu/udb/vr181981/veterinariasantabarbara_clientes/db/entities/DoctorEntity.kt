package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "doctors")
data class DoctorEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val nombre: String,
)