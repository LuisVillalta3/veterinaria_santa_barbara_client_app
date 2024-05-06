package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.data.repository

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.SharedPreferencesManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.Repository

class LoginRepository : Repository() {
    suspend fun loginUser(email: String, password: String): Boolean {
        return try {
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            sharedPreferences.saveEmail(authResult.user?.email!!)
            sharedPreferences.saveUserID(authResult.user?.uid!!)
            true
        } catch (e: Exception) {
            false
        }
    }
}