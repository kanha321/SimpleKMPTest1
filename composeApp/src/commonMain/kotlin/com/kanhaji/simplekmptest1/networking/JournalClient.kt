package com.kanhaji.simplekmptest1.networking

import com.kanhaji.simplekmptest1.util.NetworkError
import com.kanhaji.simplekmptest1.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

class JournalClient(
    private val httpClient: HttpClient
) {
    suspend fun getJournals(): Result<List<JournalEntry>, NetworkError> {
        val response: HttpResponse = try {
            httpClient.get("http://192.168.163.86:8080/_journal/get-all")
        } catch (e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch (e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
        } catch (e: Exception) {
            println("JournalClient: Exception: $e")
            return Result.Error(NetworkError.UNKNOWN)
        }

        return when (response.status.value) {
            in 200..299 -> {
                val body: List<JournalEntry> = response.body()
                Result.Success(body)
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}