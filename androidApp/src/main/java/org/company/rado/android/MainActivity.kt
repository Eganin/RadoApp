package org.company.rado.android

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
}