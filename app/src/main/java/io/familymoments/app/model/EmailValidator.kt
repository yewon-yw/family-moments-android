package io.familymoments.app.model

class EmailValidator {
    fun isValid(userId: String): Boolean {
        return userId.isNotEmpty() && "@" in userId
    }
}
