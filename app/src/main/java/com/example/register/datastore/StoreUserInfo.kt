package com.example.register.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class StoreUserInfo(private val context: Context) {

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_info")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_FAMILY = stringPreferencesKey("user_family")
        val USER_ID = stringPreferencesKey("user_id")
        val USER_DATE_OF_BIRTH = stringPreferencesKey("user_date_of_birth")
    }

    val getName: Flow<String> = context.dataStore.data.map {
        preferences -> preferences[USER_NAME]?: ""
    }

    val getFamily: Flow<String> = context.dataStore.data.map {
            preferences -> preferences[USER_FAMILY]?: ""
    }

    val getId: Flow<String> = context.dataStore.data.map {
            preferences -> preferences[USER_ID]?: ""
    }

    val getDateOfBirth: Flow<String> = context.dataStore.data.map {
            preferences -> preferences[USER_DATE_OF_BIRTH]?: ""
    }

    suspend fun saveName(name:String){
        context.dataStore.edit {
                preferences -> preferences[USER_NAME] = name
        }
    }

    suspend fun saveFamily(family:String){
        context.dataStore.edit {
                preferences -> preferences[USER_FAMILY] = family
        }
    }

    suspend fun saveId(id:String){
        context.dataStore.edit {
                preferences -> preferences[USER_ID] = id
        }
    }

    suspend fun saveDateOfBirth(dateOfBirth:String){
        context.dataStore.edit {
                preferences -> preferences[USER_DATE_OF_BIRTH] = dateOfBirth
        }
    }

    suspend fun deleteName(){
        context.dataStore.edit {
                preferences -> preferences[USER_NAME] = ""
        }
    }

    suspend fun deleteFamily(){
        context.dataStore.edit {
                preferences -> preferences[USER_FAMILY] = ""
        }
    }

    suspend fun deleteId(){
        context.dataStore.edit {
                preferences -> preferences[USER_ID] = ""
        }
    }

    suspend fun deleteDateOfBirth(){
        context.dataStore.edit {
                preferences -> preferences[USER_DATE_OF_BIRTH] = ""
        }
    }
}