package edu.uadeApps.asinomas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.uadeApps.asinomas.presentation.navigation.AppNavHost


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavHost()
        }
    }
}