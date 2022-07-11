package com.ajijul.store.repo

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun putStringStringArray(key: String, value: ArrayList<String>)
    suspend fun getStringArray(key: String): ArrayList<String>
}