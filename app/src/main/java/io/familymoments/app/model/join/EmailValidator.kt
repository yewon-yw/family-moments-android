package io.familymoments.app.model.join

object Validator{
    fun checkEmail(email: String): Boolean {
        return email.isNotEmpty() && "@" in email
    }

    fun checkPassword(password: String): Boolean {
        val regex = "^[a-zA-Z0-9]{8,12}$"
        return password.matches(Regex(regex))
    }
    fun checkId(userId: String): Boolean {
        val regex = "^[a-zA-Z0-9]{6,12}$"
        return userId.matches(Regex(regex))
    }
}
