package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories

import android.util.Log
import kotlinx.coroutines.tasks.await
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.PetEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager

class PetRepository : Repository() {
    private suspend fun getUserPetsFromFirebase(): List<PetEntity> {
        val userID = sharedPreferences.getUserID() ?: return listOf<PetEntity>()
        RoomManager.db.petsDao().deleteAll()

        return try {
            val petList = mutableListOf<PetEntity>()

            val res = db.collection("users").document(userID).collection("mascotas").get().await()
            res.documents.map { doc ->
                val docID = doc.id
                doc.data?.let {
                    val petData = PetEntity(
                        id = docID,
                        especie = it["especie"].toString(),
                        nombreMascota = it["nombre"].toString(),
                        razaDesconocida = it["razaDesconocida"].toString() == "true",
                        razaText = it["raza"].toString(),
                        fechaNacimiento = it["fechaNacimiento"].toString(),
                        especieMascotaText = it["especieText"].toString(),
                        generoMascotas = it["genero"].toString(),
                    )

                    RoomManager.db.petsDao().insert(petData)

                    petList.add(petData)
                }
            }

            petList.toList()
        } catch (e: Exception) {
            Log.d("PetRepositoryError", "Error: ${e.message}")
            listOf<PetEntity>()
        }
    }

    private suspend fun getUserPetsFromSqlite(): List<PetEntity> {
        return RoomManager.db.petsDao().getAll()
    }

    suspend fun getUserPets(): List<PetEntity> {
        return  getUserPetsFromFirebase()
    }
}
