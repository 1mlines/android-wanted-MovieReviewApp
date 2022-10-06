package com.preonboarding.domain.model

data class Review(
    val nickname: String = "",
    val password: String = "",
    val rating: Float = 0f,
    val content: String = "",
    val imageUri: String = "",
    val date: String = ""
) {
    fun toMapContent(): Map<String, Any?> {
        return mapOf(
            "password" to password,
            "rating" to rating,
            "content" to content,
            "imageUri" to imageUri,
            "date" to date
        )
    }
}
