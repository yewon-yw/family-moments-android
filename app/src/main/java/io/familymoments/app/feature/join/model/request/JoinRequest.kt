package io.familymoments.app.feature.join.model.request

data class JoinRequest(
        val id:String,
        val passwordA:String,
        val passwordB:String,
        val name:String,
        val email:String,
        val strBirthDate:String,
        val nickname:String
)
