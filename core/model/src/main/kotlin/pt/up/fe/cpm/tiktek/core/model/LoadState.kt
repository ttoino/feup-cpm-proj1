package pt.up.fe.cpm.tiktek.core.model

sealed class LoadState<out T> {
    data object Loading : LoadState<Nothing>()

    data class Success<T>(val value: T) : LoadState<T>()

    data class Error(val message: String) : LoadState<Nothing>()
}
