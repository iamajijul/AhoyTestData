package com.ajijul.ahoytestdata.utils

import android.view.View

interface MessageHandler {
    fun showSnackErrorWithAction(view: View, msg: Int, action: () -> Unit)
    fun showSnackErrorWithAction(view: View, msg: String, action: () -> Unit)
    fun showSnackSuccess(view: View, msg: Int, long: Boolean = false)

}