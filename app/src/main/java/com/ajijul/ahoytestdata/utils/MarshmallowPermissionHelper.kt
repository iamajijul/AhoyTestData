package com.ajijul.ahoytestdata.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Created by Ajijul Mondal
 */
object MarshmallowPermissionHelper {
    /*Permission Checking for Location*/
    fun getLocationPermission(
        fragment: Fragment?,
        activity: Activity,
        REQUEST_CODE: Int
    ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            if (ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) ||
                    ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    val builder =
                        AlertDialog.Builder(
                            activity)
                    builder.setTitle("Permission Required")
                    builder.setMessage(
                        "Location Permission Required. " +
                                "You have to grant this permission in order to use this feature."
                    )
                    builder.setPositiveButton(
                        "Got it"
                    ) { dialog, _ ->
                        dialog.dismiss()
                        if (fragment != null) fragment.requestPermissions(
                            arrayOf(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ),
                            REQUEST_CODE
                        ) else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            activity.requestPermissions(
                                arrayOf(
                                    Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                ),
                                REQUEST_CODE
                            )
                        }
                    }
                    builder.show()
                } else {
                    if (fragment != null) fragment.requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        REQUEST_CODE
                    ) else activity.requestPermissions(
                        arrayOf(
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ),
                        REQUEST_CODE
                    )
                }
                false
            } else true
        } else {
            true
        }
    }

    fun isLocationPermissionActive(activity: Activity): Boolean {
        val manager =
            activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Here, thisActivity is the current activity
            (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            )) && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }


}