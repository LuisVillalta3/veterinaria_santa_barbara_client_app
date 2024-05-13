package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories

import android.util.Log
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.tasks.await
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.DoctorEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager

class DoctorRepository : Repository() {
    private suspend fun getDoctorsDataFromFirebase(): List<DoctorEntity> {
        return try {
            val res = db.collection("doctors").get().await()

            val mutableList = mutableListOf<DoctorEntity>()

            res.documents.map {
                val doctorId = it.id
                it?.data?.let { data ->
                    val doctorData = DoctorEntity(
                        id = doctorId,
                        nombre = data["nombre"].toString(),
                    )

                    mutableList.add(doctorData)
                }
            }

            mutableList.toList()
        } catch (e: Exception) {
            Log.d("UserRepositoryError", "Error: ${e.message}")
            listOf<DoctorEntity>()
        }
    }

    suspend fun getDoctorsData(): List<DoctorEntity> {
        return getDoctorsDataFromFirebase()
    }
}