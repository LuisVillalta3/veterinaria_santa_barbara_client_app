package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.entities.NotificationEntity

@Dao
interface NotificationsDao {
    @Query("SELECT * FROM notifications ORDER BY date DESC")
    suspend fun getNotifications(): NotificationEntity

    @Insert
    suspend fun insert(vararg noti: NotificationEntity)

    @Delete
    suspend fun delete(noti: NotificationEntity)

    @Query("DELETE FROM user")
    suspend fun deleteAll()
}