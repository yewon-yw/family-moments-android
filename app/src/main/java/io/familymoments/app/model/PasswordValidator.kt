package io.familymoments.app.model

class PasswordValidator {
    fun isValid(password: String): Boolean {
        val regex = "^[a-zA-Z0-9]{8,12}$"
        return password.matches(Regex(regex))
    }
}