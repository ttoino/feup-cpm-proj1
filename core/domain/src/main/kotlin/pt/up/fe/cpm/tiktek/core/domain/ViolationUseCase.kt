package pt.up.fe.cpm.tiktek.core.domain

import androidx.annotation.StringRes
import pt.up.fe.cpm.tiktek.core.model.validation.Violation

object ViolationUseCase {
    @StringRes
    operator fun invoke(violation: Violation) =
        when (violation) {
            Violation.UNKNOWN -> R.string.error_unknown
            Violation.REQUIRED -> R.string.violation_required
            Violation.NIF -> R.string.violation_nif
            Violation.NIF_CHECK -> R.string.violation_nif_check
            Violation.NUMBER_CC -> R.string.violation_number_cc
            Violation.NUMBER_CC_CHECK -> R.string.violation_number_cc_check
            Violation.EMAIL -> R.string.violation_email
            Violation.EMAIL_ALREADY_EXISTS -> R.string.validation_email_already_exists
            Violation.EXPIRATION_DATE_CC -> R.string.violation_expiration_date_cc
            Violation.CVC_CC -> R.string.violation_cvc_cc
            Violation.PASSWORD -> R.string.violation_password
            Violation.BIRTHDATE -> R.string.violation_birthdate
            Violation.LOGIN -> R.string.violation_login
        }

    @StringRes
    operator fun invoke(violation: String) =
        try {
            invoke(Violation.valueOf(violation))
        } catch (e: IllegalArgumentException) {
            R.string.error_unknown
        }
}
