package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.domain

import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.model.UserTempData
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.register.data.repository.RegisterRepository

open class RegisterController {
    protected val registerRepository = RegisterRepository()

    fun removeTempUserFromCache(): Boolean {
        return registerRepository.removeTempUserFromCache()
    }

    fun getTempUserData(): UserTempData? {
        return registerRepository.getTempUserData()
    }
}
