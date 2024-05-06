package sv.edu.udb.vr181981.veterinariasantabarbara_clientes.db.managers

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import sv.edu.udb.vr181981.veterinariasantabarbara_clientes.MyApp
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object CacheManager {
    @OptIn(DelicateCoroutinesApi::class)
    fun saveDataToCacheAsync(data: String, fileName: String) {
        GlobalScope.launch(Dispatchers.IO) {
            saveDataToCache(data, fileName)
        }
    }

    private fun saveDataToCache(data: String, fileName: String) {
        var fos: FileOutputStream? = null
        try {
            val file = File(MyApp.instance.cacheDir, fileName)
            fos = FileOutputStream(file)
            fos.write(data.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun deleteDataFromCache(fileName: String): Boolean {
        val file = File(MyApp.instance.cacheDir, fileName)
        return file.delete()
    }

    fun updateDataInCache(data: String, fileName: String): Boolean {
        // First, delete existing data
        val deleted = deleteDataFromCache(fileName)
        if (!deleted) {
            // Handle failure to delete existing data
            return false
        }

        // Save new data
        val file = File(MyApp.instance.cacheDir, fileName)
        return try {
            FileOutputStream(file).use { fos ->
                fos.write(data.toByteArray())
                true // Data updated successfully
            }
        } catch (e: IOException) {
            e.printStackTrace()
            false // Failed to update data
        }
    }

    fun getDataFromCache(fileName: String): String? {
        val file = File(MyApp.instance.cacheDir, fileName)
        return try {
            file.readText()
        } catch (e: IOException) {
            e.printStackTrace()
            null // Return null if there was an error reading the file
        }
    }
}
