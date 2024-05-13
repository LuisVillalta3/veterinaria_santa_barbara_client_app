package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers

import androidx.room.Database
import androidx.room.RoomDatabase
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.dao.PetsDao
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.dao.UserDao
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.DoctorEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.dao.NotificationsDao
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.entities.NotificationEntity

@Database(
    entities = [UserEntity::class, PetEntity::class, NotificationEntity::class, DoctorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun petsDao(): PetsDao
    abstract fun notificationsDao(): NotificationsDao
}
