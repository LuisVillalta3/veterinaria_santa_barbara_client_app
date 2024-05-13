package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.repositories.Repository

class NotificationsRepository : Repository() {
    suspend fun registerDeviceToken(token: String) {
        val userID = sharedPreferences.getUserID()

        withContext(Dispatchers.IO) {
            val tokens = findDeviceToken()

            if (tokens.contains(token)) {
                return@withContext
            }

            db.collection("tokens")
                .document(userID!!)
                .collection("tokens")
                .document(token)
                .set(mapOf("token" to token))
                .await()
        }
    }

    private suspend fun findDeviceToken(): List<String> {
        val userID = sharedPreferences.getUserID() ?: return listOf<String>()

        val userTokens = db.collection("tokens")
            .document(userID)
            .collection("tokens")
            .get()
            .await()

        if (userTokens.isEmpty) {
            return listOf<String>()
        }

        return userTokens.documents.map { it.id }
    }
}