package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.services

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers.RoomManager
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.modules.notifications.data.entities.NotificationEntity

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // Handle the message
        val notification = NotificationEntity(
            id = message.messageId!!,
            title = message.notification?.title ?: "",
            message = message.notification?.body ?: "",
            date = message.sentTime
        )

        CoroutineScope(Dispatchers.IO).launch {
            RoomManager.db.notificationsDao().insert(notification)
        }
    }
}