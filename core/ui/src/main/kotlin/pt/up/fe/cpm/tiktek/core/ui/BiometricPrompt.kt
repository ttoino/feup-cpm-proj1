package pt.up.fe.cpm.tiktek.core.ui

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

tailrec fun Context.getActivity(): FragmentActivity? =
    when (this) {
        is FragmentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

@ViewModelScoped
class BiometricPrompt
    @Inject
    constructor(
//        @ApplicationContext private val context: Context,
//        private val activity: FragmentActivity,
    ) {
        private val resultChannel = Channel<BiometricResult>()
        val promptResults = resultChannel.receiveAsFlow()

        fun showBiometricPrompt(
            title: String,
            description: String,
            activity: FragmentActivity,
        ) {
            val manager = BiometricManager.from(activity)
            val authenticators =
                if (Build.VERSION.SDK_INT >= 30) {
                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                } else {
                    BIOMETRIC_STRONG
                }
            val promptInfo =
                BiometricPrompt.PromptInfo.Builder()
                    .setTitle(title)
                    .setDescription(description)
                    .setAllowedAuthenticators(authenticators)

            if (Build.VERSION.SDK_INT < 30) {
                promptInfo.setNegativeButtonText("Cancel")
            }

            when (manager.canAuthenticate(authenticators)) {
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    resultChannel.trySend(BiometricResult.HardwareUnavailable)
                    return
                }
                BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                    resultChannel.trySend(BiometricResult.FeatureUnavailable)
                    return
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    resultChannel.trySend(BiometricResult.AuthenticationNotSet)
                    return
                }
                else -> Unit
            }
            val prompt =
                BiometricPrompt(
                    activity,
                    object : BiometricPrompt.AuthenticationCallback() {
                        override fun onAuthenticationError(
                            errorCode: Int,
                            errString: CharSequence,
                        ) {
                            super.onAuthenticationError(errorCode, errString)
                            resultChannel.trySend(BiometricResult.AuthenticationError(errString.toString()))
                        }

                        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                            super.onAuthenticationSucceeded(result)
                            resultChannel.trySend(BiometricResult.AuthenticationSuccess)
                        }

                        override fun onAuthenticationFailed() {
                            super.onAuthenticationFailed()
                            resultChannel.trySend(BiometricResult.AuthenticationFailed)
                        }
                    },
                )
            prompt.authenticate(promptInfo.build())
        }

        sealed interface BiometricResult {
            data object HardwareUnavailable : BiometricResult

            data object FeatureUnavailable : BiometricResult

            data class AuthenticationError(val error: String) : BiometricResult

            data object AuthenticationFailed : BiometricResult

            data object AuthenticationSuccess : BiometricResult

            data object AuthenticationNotSet : BiometricResult
        }
    }
