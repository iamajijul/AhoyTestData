package com.ajijul.store.repo

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ajijul.store.utils.dataStore
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreRepositoryImplementation @Inject constructor(
    private val context: Context
) : DataStoreRepository {
    override suspend fun putString(key: String, value: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            preferences[preferencesKey]
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun putStringStringArray(key: String, value: ArrayList<String>) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = convertArrayToString(value)
        }
    }

    override suspend fun getStringArray(key: String): ArrayList<String> {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            convertStringToArray(preferences[preferencesKey])
        } catch (e: Exception) {
            e.printStackTrace()
            arrayListOf()
        }
    }

    private fun convertArrayToString(value: ArrayList<String>): String {
        return value.joinToString(
            separator = ",",
            transform = { it })
    }

    private fun convertStringToArray(value: String?): ArrayList<String> {
        with(
            if (value?.isNotEmpty() == true) value.split(',') else return arrayListOf()
        ) {
            return ArrayList(this)
        }
    }
}