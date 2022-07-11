package com.ajijul.ahoytestdata.store

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ajijul.ahoytestdata.utils.dataStore
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

    override suspend fun putStringStringArray(key: String, value: Set<String>) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = convertArrayToString(value)
        }
    }

    override suspend fun getStringArray(key: String): HashSet<String> {
        return try {
            val preferencesKey = stringPreferencesKey(key)
            val preferences = context.dataStore.data.first()
            convertStringToArray(preferences[preferencesKey])
        } catch (e: Exception) {
            e.printStackTrace()
            hashSetOf()
        }
    }

    private fun convertArrayToString(value: Set<String>): String {
        return value.joinToString(
            separator = ",",
            transform = { it })
    }

    private fun convertStringToArray(value: String?): HashSet<String> {
        with(
            if (value?.isNotEmpty() == true) value.split(',') else return hashSetOf()
        ) {
            return HashSet(this)
        }
    }
}