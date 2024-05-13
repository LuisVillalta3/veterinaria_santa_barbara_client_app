package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories

import android.util.Log
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.tasks.await
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.entities.UserEntity
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager

class UserRepository : Repository() {
    private suspend fun getUserDataFromFirebase(): UserEntity? {
        val userID = sharedPreferences.getUserID() ?: return null

        return try {
            val res = db.collection("users").document(userID).get().await()
            res.data?.let {
                val userData = UserEntity(
                    id = userID,
                    name = it["nombre"].toString(),
                    email = it["email"].toString(),
                    telefono = it["telefono"].toString(),
                    lastname = it["apellido"].toString(),
                    fechaNacimiento = it["fechaNacimiento"].toString(),
                )

                RoomManager.db.userDao().insertUser(userData)
                userData
            }
        } catch (e: Exception) {
            Log.d("UserRepositoryError", "Error: ${e.message}")
            null
        }
    }

    private suspend fun getUserDataFromSqlite(): UserEntity? {
        return RoomManager.db.userDao().getUser()
    }

    suspend fun getUserData(): UserEntity? {
        return getUserDataFromSqlite() ?: getUserDataFromFirebase()
    }
}