package com.ajijul.ahoytestdata.store

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
    suspend fun putStringStringArray(key: String, value: Set<String>)
    suspend fun getStringArray(key: String): HashSet<String>
}