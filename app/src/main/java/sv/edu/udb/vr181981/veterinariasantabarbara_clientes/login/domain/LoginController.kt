package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.login.data.repository.LoginRepository

class LoginController {
    private val loginRepository = LoginRepository()

    suspend fun loginUser(email: String, password: String): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext loginRepository.loginUser(email, password)
        }
    }
}