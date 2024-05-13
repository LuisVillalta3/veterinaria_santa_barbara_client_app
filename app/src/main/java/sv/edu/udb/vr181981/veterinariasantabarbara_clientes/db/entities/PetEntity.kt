package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pets")
data class PetEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val nombreMascota: String,
    @ColumnInfo val especie: String,
    @ColumnInfo val especieMascotaText: String,
    @ColumnInfo val razaDesconocida: Boolean,
    @ColumnInfo val razaText: String,
    @ColumnInfo val generoMascotas: String,
    @ColumnInfo val fechaNacimiento: String,
)
