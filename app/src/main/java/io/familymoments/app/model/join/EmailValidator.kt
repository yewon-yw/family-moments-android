package io.familymoments.app.model.join

class EmailValidator {
    fun isValid(userId: String): Boolean {
        return userId.isNotEmpty() && "@" in userId
    }
}
