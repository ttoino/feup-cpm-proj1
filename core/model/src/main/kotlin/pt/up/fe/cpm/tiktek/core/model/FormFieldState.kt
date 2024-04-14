package pt.up.fe.cpm.tiktek.core.model

data class FormFieldState<out T>(
    val value: T,
    val error: String = "",
    val showError: Boolean = false,
) {
    val valid get() = error.isNullOrBlank()

    fun <R> map(fn: (T) -> R): FormFieldState<R> =
        FormFieldState(
            fn(value),
            error,
            showError,
        )
}
