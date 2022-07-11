package com.ajijul.store.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ajijul.ahoytestdata.store.PREFERENCES_NAME

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
