package io.familymoments.app.feature.signup.model.uistate

import io.familymoments.app.core.component.CheckedStatus
import java.io.File

data class SignUpUiState(
    val signUpInfoUiState: SignUpInfoUiState = SignUpInfoUiState(),
    val signUpValidatedUiState: SignUpValidatedUiState = SignUpValidatedUiState(),
    val signUpTermUiState: SignUpTermUiState = SignUpTermUiState(),
    val signUpResultUiState: SignUpResultUiState = SignUpResultUiState()
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
    val userIdFormValidated: Boolean = false,
    val userIdDuplicatedUiState: UserIdDuplicatedUiState = UserIdDuplicatedUiState(),
    val passwordFormValidated: Boolean = false,
    val emailFormValidated: Boolean = false,
    val emailDuplicatedUiState: EmailDuplicatedUiState = EmailDuplicatedUiState(),
    val nicknameFormValidated: Boolean = false,
    val birthDayFormValidated: Boolean = false
)

data class SignUpTermUiState(
    val isEssential: Boolean = false,
    val description: Int = 0,
    val checkedStatus: CheckedStatus = CheckedStatus.UNCHECKED
)

data class EmailDuplicatedUiState(
    val isSuccess: Boolean? = null,
    val duplicatedPass: Boolean = false
)

data class UserIdDuplicatedUiState(
    val isSuccess: Boolean? = null,
    val duplicatedPass: Boolean = false
)

data class SignUpResultUiState(
    val isSuccess: Boolean? = null,
    val message: String = ""
)
