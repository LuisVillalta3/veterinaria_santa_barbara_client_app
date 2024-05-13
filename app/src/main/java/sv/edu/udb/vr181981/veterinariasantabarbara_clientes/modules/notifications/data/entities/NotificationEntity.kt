package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    @ColumnInfo val title: String,
    @ColumnInfo val message: String,
    @ColumnInfo val date: Long
)
