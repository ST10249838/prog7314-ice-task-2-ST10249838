package com.st10249838.ice2

/**
 * Data class representing the JSON response from the chat API.
 * The property 'text' must match the key in the JSON object.
 * e.g., {"text": "This is a response."}
 */
data class ChatResponse(val text: String)

/**
 * Data class representing the JSON request sent to the chat API.
 * The property 'prompt' must match the key expected by the API.
 * e.g., {"prompt": "What is the best car?"}
 */
data class ChatRequest(val prompt: String)

/**
 * An enum to distinguish between messages from the user and the AI model.
 */
enum class Author {
    USER, MODEL
}

/**
 * A data class to represent a single chat message.
 * @param text The content of the message.
 * @param author The author of the message (USER or MODEL).
 */
data class ChatMessage(
    val text: String,
    val author: Author
)