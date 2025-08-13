package com.st10249838.ice2

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A composable function that displays a single chat message in a bubble.
 * The appearance of the bubble (alignment, color, shape) changes based on whether
 * the message is from the user or the model.
 * @param message The ChatMessage object to display.
 */
@Composable
fun ChatBubble(message: ChatMessage) {
    // Determine if the message is from the current user.
    val isUserMessage = message.author == Author.USER

    // Align user messages to the end (right) and model messages to the start (left).
    val alignment = if (isUserMessage) Alignment.CenterEnd else Alignment.CenterStart

    // Set different bubble colors for user and model messages for better visual distinction.
    val bubbleColor =
        if (isUserMessage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondaryContainer
    val textColor =
        if (isUserMessage) Color.White else MaterialTheme.colorScheme.onSecondaryContainer

    // Apply a unique shape to the corners of the bubble to create a "speech" effect.
    val bubbleShape = if (isUserMessage) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    } else {
        RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
    }

    // Use a Box to align the chat bubble within the full width of the screen.
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        contentAlignment = alignment
    ) {
        // A nested Box to constrain the width of the bubble itself.
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f) // The bubble will take up to 80% of the available width.
        ) {
            // The Text composable that displays the actual message content.
            Text(
                text = message.text,
                modifier = Modifier
                    .align(alignment) // Align the text within the constrained Box.
                    .clip(bubbleShape) // Apply the rounded corner shape.
                    .background(bubbleColor) // Set the background color.
                    .padding(12.dp), // Add padding inside the bubble.
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}