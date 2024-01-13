package io.familymoments.app.model

class UserIdValidator {
    fun isValid(userId: String): Boolean {
        val regex = "^[a-zA-Z0-9]{6,12}$"
        return userId.matches(Regex(regex))
    }
}
