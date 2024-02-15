package io.familymoments.app.model.response

sealed class UserErrorResponse:Throwable() {
    data object RefreshTokenExpiration:UserErrorResponse()
    data class CommonError(override val message:String):UserErrorResponse()
}
