package com.ajijul.ahoytestdata.utils

import android.content.Context
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ajijul.ahoytestdata.store.PREFERENCES_NAME
import com.google.android.material.snackbar.Snackbar

fun View.indefiniteSnackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE)
fun View.indefiniteSnackbar(msg: String) = Snackbar.make(this, msg, Snackbar.LENGTH_INDEFINITE)
fun View.longSnackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_LONG)
fun View.snackbar(msg: Int) = Snackbar.make(this, msg, Snackbar.LENGTH_SHORT)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)
