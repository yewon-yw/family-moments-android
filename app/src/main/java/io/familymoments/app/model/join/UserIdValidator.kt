package io.familymoments.app.model.join

class UserIdValidator {
    fun isValid(userId: String): Boolean {
        val regex = "^[a-zA-Z0-9]{6,12}$"
        return userId.matches(Regex(regex))
    }
}
