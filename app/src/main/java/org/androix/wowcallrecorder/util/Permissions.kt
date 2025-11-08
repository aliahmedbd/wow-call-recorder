package org.androix.wowcallrecorder.util

import android.Manifest
import android.os.Build

object Permissions {
    val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_CALL_LOG,
        )
    } else {
        listOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.READ_CALL_LOG,
        )
    }
}
