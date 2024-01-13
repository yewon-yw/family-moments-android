package io.familymoments.app.model

data class CheckIdRequest(val id: String)
data class CheckEmailRequest(val email:String)
data class JoinRequest(
        val id:String,
        val passwordA:String,
        val passwordB:String,
        val name:String,
        val email:String,
        val strBirthDate:String,
        val nickname:String
)