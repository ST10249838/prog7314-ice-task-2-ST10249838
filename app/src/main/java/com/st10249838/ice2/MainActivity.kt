package com.st10249838.ice2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.st10249838.ice2.ui.theme.Ice2Theme

/**
 * The main entry point of the application.
 */
class MainActivity : ComponentActivity() {
    // Lazily initializes the ChatViewModel. The 'by viewModels()' delegate
    // ensures the ViewModel survives configuration changes (like screen rotation).
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent is the entry point for Jetpack Compose UI.
        setContent {
            // Ice2Theme is a custom theme for the application.
            Ice2Theme {
                // A Surface container using the 'background' color from the theme.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Display the main ChatScreen, passing the ViewModel instance.
                    ChatScreen(viewModel = viewModel)
                }
            }
        }
    }
}