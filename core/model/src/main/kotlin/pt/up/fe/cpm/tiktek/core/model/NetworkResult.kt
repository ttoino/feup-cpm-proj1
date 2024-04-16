package pt.up.fe.cpm.tiktek.core.model

sealed class NetworkResult<out T> {
    data class Success<T>(
        val value: T,
    ) : NetworkResult<T>() {
        override fun <R> map(fn: (T) -> R) = Success(fn(value))

        override fun getOrNull() = value
    }

    data class Error(
        val error: ErrorResponse,
    ) : NetworkResult<Nothing>() {
        override fun <R> map(fn: (Nothing) -> R) = this
    }

    data object Failure : NetworkResult<Nothing>() {
        override fun <R> map(fn: (Nothing) -> R) = this
    }

    abstract fun <R> map(fn: (T) -> R): NetworkResult<R>

    open fun getOrNull(): T? = null
}
