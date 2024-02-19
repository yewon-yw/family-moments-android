package io.familymoments.app.feature.join.model

object UserInfoFormatChecker{
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
    fun checkNickname(nickname: String): Boolean {
        val regex = Regex("[a-zA-Z0-9]+")
        return nickname.matches(regex)
    }
}
