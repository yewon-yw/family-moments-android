package io.familymoments.app.feature.splash.viewmodel

import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.familymoments.app.core.base.BaseViewModel
import io.familymoments.app.core.network.repository.UserRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    enum class NextRoute {
        INIT,
        LOGIN,
        MAIN
    }

    val currentRoute = MutableLiveData(NextRoute.INIT)

    suspend fun autoSignIn() {
        delay(2000)
        async(
            operation = {
                userRepository.autoSignIn()
            },
            onSuccess = {
                if (it.isSuccess) {
                    currentRoute.value = NextRoute.MAIN
                } else {
                    currentRoute.value = NextRoute.LOGIN
                }
            },
            onFailure = {
                currentRoute.value = NextRoute.LOGIN
            }
        )
    }
}
