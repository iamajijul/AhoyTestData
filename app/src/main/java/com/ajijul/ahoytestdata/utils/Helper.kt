package com.ajijul.ahoytestdata.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

object Helper {
    fun hideKeyboard(context: Activity?){
        // Check if no view has focus:
        val view = context?.currentFocus
        view?.let { v ->
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
}