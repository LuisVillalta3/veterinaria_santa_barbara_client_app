package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user LIMIT 1")
    suspend fun getUser(): UserEntity

    @Insert
    suspend fun insertUser(vararg user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

    @Query("DELETE FROM user")
    suspend fun deleteUserLogin()

    @Update
    suspend fun updateUser(vararg user: UserEntity)
}