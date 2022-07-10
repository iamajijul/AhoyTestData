package com.ajijul.ahoytestdata.utils

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ajijul.ahoytestdata.R
import javax.inject.Inject

class MessageHandlerImp @Inject constructor() : MessageHandler {

    override fun showSnackErrorWithAction(view: View, msg: Int, action: () -> Unit) {
        val snackbar = view.indefiniteSnackbar(msg)
        snackbar.setAction(R.string.retry_text) { action() }
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.colorRedError
            )
        )
        snackbar.setTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.setActionTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.show()
    }

    override fun showSnackErrorWithAction(view: View, msg: String, action: () -> Unit) {
        val snackbar = view.indefiniteSnackbar(msg)
        snackbar.setAction(R.string.retry_text) { action() }
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                R.color.colorRedError
            )
        )
        snackbar.setTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.setActionTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.show()
    }

    override fun showSnackSuccess(view: View, msg: Int, long: Boolean) {
        val snackbar = if (!long) view.snackbar(msg)
        else view.longSnackbar(msg)
        val textView =
            snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView //Get reference of snackbar textview
        textView.maxLines = 5
        snackbar.view.setBackgroundColor(ContextCompat.getColor(view.context, R.color.colorSuccess))
        snackbar.setTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.setActionTextColor( ContextCompat.getColor(
            view.context,
            R.color.white
        ))
        snackbar.show()
    }
}