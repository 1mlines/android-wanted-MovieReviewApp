package com.preonboarding.domain.model

data class Review(
    val nickname: String,
    val password: String,
    val rating: Int,
    val content: String = "",
    val imageUrl: String = "",
    val date: String
) {
    fun toMapContent(): Map<String, Any?> {
        return mapOf(
            "password" to password,
            "rating" to rating,
            "content" to content,
            "imageUrl" to imageUrl,
            "date" to date
        )
    }
}
