package com.example.bitego

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bitego.navigation.AppNavigation
import com.example.bitego.ui.theme.BiteGoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            BiteGoTheme {
                AppNavigation()
            }
        }
    }
}