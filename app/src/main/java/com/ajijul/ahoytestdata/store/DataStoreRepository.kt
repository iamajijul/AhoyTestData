package com.ajijul.ahoytestdata.store

interface DataStoreRepository {
    suspend fun putString(key: String, value: String)
    suspend fun getString(key: String): String?
}