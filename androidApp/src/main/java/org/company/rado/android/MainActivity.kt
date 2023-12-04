package org.company.rado.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import navigation.App
import picker.LocalMediaController
import platform.Platform

class MainActivity : ComponentActivity() {
    private val localMediaController = LocalMediaController(context=this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(platform = Platform.Andorid,localMediaController=localMediaController)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        localMediaController.permissionsController.onDataReceived(
            requestCode,
            permissions,
            grantResults
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        localMediaController.onActivityResult(requestCode, resultCode, data)
    }
}