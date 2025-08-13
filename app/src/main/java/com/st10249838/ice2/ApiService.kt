package com.st10249838.ice2

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Defines the API endpoints for the chat service.
 * This interface is used by Retrofit to generate the network request code.
 */
interface ApiService {
    /**
     * Sends a chat message to the server.
     * @param request The ChatRequest object containing the user's prompt.
     * @return A ChatResponse object with the model's reply.
     */
    // The @POST annotation specifies that this is a POST request.
    // The "generate/" string is the path that will be appended to the base URL.
    @POST("generate/")
    suspend fun sendMessage(@Body request: ChatRequest): ChatResponse
}