package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers

import androidx.room.Room
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.MyApp

object RoomManager {
    private const val DATABASE_NAME = "veterinaria_santa_barbara_db"

    val db = Room.databaseBuilder(
        MyApp.instance.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()
}