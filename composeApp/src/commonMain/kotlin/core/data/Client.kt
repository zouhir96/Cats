package core.data


import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

object Constants {
    const val BASE_URL = "https://api.thecatapi.com/v1/"
    const val API_KEY = "live_7vZpvRYB1J33tihb8uC3gEAj8ziF0ELPRjAtD9mZxXAEbG4fpzcTUHrZ1bLguM8d"
    const val API_KEY_HEADER = "x-api-key"
}

val httpClient = HttpClient {
    defaultRequest {
        url(Constants.BASE_URL)
        header(Constants.API_KEY_HEADER, Constants.API_KEY)
    }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }
    install(Logging){
        logger = Logger.SIMPLE
        level = LogLevel.BODY
    }
}