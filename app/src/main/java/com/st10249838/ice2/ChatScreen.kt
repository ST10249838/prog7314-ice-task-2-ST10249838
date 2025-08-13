package com.st10249838.ice2

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * The main screen for the chat application.
 * It displays the list of messages and the input field for sending new messages.
 * @param viewModel The ChatViewModel that holds the state of the chat.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    // Observe the messages and loading state from the ViewModel.
    val messages by viewModel.messages
    val isLoading by viewModel.isLoading
    // State for the LazyColumn to control scrolling.
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // A side effect that triggers whenever the 'messages' list changes.
    // It automatically scrolls the list to the newest message.
    LaunchedEffect(messages) {
        coroutineScope.launch {
            if (messages.isNotEmpty()) {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    // Scaffold provides a standard layout structure (top bar, content, etc.).
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CarGPT") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        // The main content of the screen.
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues) // Apply padding provided by the Scaffold.
            ) {
                // A vertically scrolling list that only composes and lays out the currently visible items.
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .weight(1f) // The list takes up all available vertical space.
                        .padding(horizontal = 8.dp),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    items(messages) { message ->
                        ChatBubble(message = message)
                    }
                }

                // Show a loading indicator when waiting for a response from the API.
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                // The input field and send button at the bottom of the screen.
                ChatInput(
                    onMessageSend = { message ->
                        viewModel.sendMessage(message)
                    },
                    isLoading = isLoading
                )
            }
        }
    )
}

/**
 * A composable for the text input field and send button.
 * @param onMessageSend A callback function to be invoked when the user sends a message.
 * @param isLoading A boolean to disable the input field and button while a message is being sent.
 */
@Composable
fun ChatInput(onMessageSend: (String) -> Unit, isLoading: Boolean) {
    // State to hold the text currently in the input field.
    var inputText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                // These modifiers ensure the input field moves up when the keyboard is shown.
                .navigationBarsPadding()
                .imePadding(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                modifier = Modifier.weight(1f), // The text field expands to fill the available width.
                placeholder = { Text("Ask about cars...") },
                enabled = !isLoading, // Disable the field when loading.
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                ),
                shape = RoundedCornerShape(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            IconButton(
                onClick = {
                    // Only send the message if it's not blank.
                    if (inputText.isNotBlank()) {
                        onMessageSend(inputText)
                        inputText = "" // Clear the input field after sending.
                    }
                },
                // Disable the button if the input is blank or if a message is currently loading.
                enabled = inputText.isNotBlank() && !isLoading
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send Message",
                    // Change the icon's tint to indicate whether it's enabled or disabled.
                    tint = if (inputText.isNotBlank() && !isLoading) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.4f
                    )
                )
            }
        }
    }
}