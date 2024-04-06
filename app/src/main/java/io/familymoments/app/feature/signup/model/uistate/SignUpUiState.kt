package io.familymoments.app.feature.signup.model.uistate

import io.familymoments.app.core.component.CheckedStatus
import java.io.File

data class SignUpUiState(
    val signUpInfoUiState: SignUpInfoUiState = SignUpInfoUiState(),
    val signUpValidatedUiState: SignUpValidatedUiState = SignUpValidatedUiState(),
    val signUpTermUiState: SignUpTermUiState = SignUpTermUiState(),
    val signUpSuccess: Boolean? = null
)

data class SignUpInfoUiState(
    val id: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val birthDay: String = "",
    val nickname: String = "",
    val imgFile: File? = null
)

data class SignUpValidatedUiState(
    val userIdValidated: Boolean = false,
    val userIdDuplicated: UserIdDuplicated = UserIdDuplicated(),
    val passwordValidated: Boolean = false,
    val emailValidated: Boolean = false,
    val emailDuplicated: EmailDuplicated = EmailDuplicated(),
    val nicknameValidated: Boolean = false,
    val birthDayValidated:Boolean = false
)

data class SignUpTermUiState(
    val isEssential: Boolean = false,
    val description: Int = 0,
    val checkedStatus: CheckedStatus = CheckedStatus.UNCHECKED
)

data class EmailDuplicated(
    val isSuccess:Boolean? = null,
    val duplicatedPass:Boolean = false
)

data class UserIdDuplicated(
    val isSuccess:Boolean? = null,
    val duplicatedPass:Boolean = false
)
