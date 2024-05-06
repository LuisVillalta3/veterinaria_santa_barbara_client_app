package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity

@Dao
interface PetsDao {
    @Query("SELECT * FROM pets")
    suspend fun getAll(): List<PetEntity>

    @Query("SELECT * FROM pets WHERE id = :id")
    suspend fun getById(id: String): PetEntity

    @Insert
    suspend fun insert(vararg pet: PetEntity)

    @Delete
    suspend fun deletePet(pet: PetEntity)

    @Query("DELETE FROM pets")
    suspend fun deleteAll()

    @Update
    suspend fun update(vararg pet: PetEntity)
}
