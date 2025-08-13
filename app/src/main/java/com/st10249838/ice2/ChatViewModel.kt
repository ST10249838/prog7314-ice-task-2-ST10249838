package com.st10249838.ice2

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * The ViewModel for the chat screen. It holds the chat state and handles business logic.
 */
class ChatViewModel : ViewModel() {
    // A mutable state list of chat messages. Jetpack Compose will automatically observe this for changes.
    val messages = mutableStateOf<List<ChatMessage>>(emptyList())

    // A mutable state boolean to track whether a network request is in progress.
    val isLoading = mutableStateOf(false)

    /**
     * Sends a user's message to the API and updates the chat state.
     * @param userInput The text entered by the user.
     */
    fun sendMessage(userInput: String) {
        // Add the user's message to the list immediately for a responsive UI.
        messages.value = messages.value + ChatMessage(userInput, Author.USER)
        // Set loading state to true to show a progress indicator.
        isLoading.value = true

        // Launch a coroutine in the ViewModel's scope to handle the network request.
        // This ensures the coroutine is cancelled if the ViewModel is destroyed.
        viewModelScope.launch {
            try {
                // Create the request object.
                val request = ChatRequest(prompt = userInput)
                // Call the API using the Retrofit instance.
                val response = RetrofitClient.instance.sendMessage(request)

                // Add the model's response to the message list.
                messages.value = messages.value + ChatMessage(response.text, Author.MODEL)

            } catch (e: Exception) {
                // If an error occurs, add an error message to the chat.
                messages.value = messages.value + ChatMessage(
                    "Error: An issue occurred. ${e.message}",
                    Author.MODEL
                )
            } finally {
                // Set loading state to false regardless of success or failure.
                isLoading.value = false
            }
        }
    }
}