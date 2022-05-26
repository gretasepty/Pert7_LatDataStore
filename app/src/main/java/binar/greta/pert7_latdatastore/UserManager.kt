package binar.greta.pert7_latdatastore

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.Preferences
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context: Context) {

    private val dataUser : DataStore<Preferences> = context.createDataStore("user_prefs")

    companion object{
        val USERNAME = preferencesKey<String>("USER_NAME")
    }

    suspend fun saveData(username : String){
        dataUser.edit {
         it[USERNAME] = username
        }
    }

    val userName : Flow<String> = dataUser.data.map {
        it[USERNAME]?: ""
    }
}