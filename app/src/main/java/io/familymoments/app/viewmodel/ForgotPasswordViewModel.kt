package io.familymoments.app.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {


}
