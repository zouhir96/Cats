package core.domain

sealed interface Error

enum class NetworkErrors: Error {
    NO_INTERNET,
    REQUEST_TIMEOUT,
    UNKNOWN
}