package com.ajijul.ahoytestdata.ui.main.weather

import com.ajijul.store.repo.DataStoreRepository

class FakeDataStoreRepository : DataStoreRepository {
    override suspend fun putString(key: String, value: String) {
    }

    override suspend fun getString(key: String): String? {
        return null
    }

    override suspend fun putStringStringArray(key: String, value: ArrayList<String>) {
    }

    override suspend fun getStringArray(key: String): ArrayList<String> {
        return arrayListOf()
    }
}