package com.example.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toApiException(): ApiException {
    return this.let {e->
        var code = 0
        val message = if(e is HttpException) {
            code = e.code()
            e.response()?.errorBody()?.string()?.let {
                val json = Json { prettyPrint = true }
                json.decodeFromString(Error.serializer(),it)
            }?.message ?: "Something went wrong"
        }else {
            e.message
        }
        ApiException(code, message?:"Something went wrong")
    }
}
class ApiException(val statusCode: Int, val errorMessage: String):IOException(errorMessage)

@Serializable
data class Error(val message: String)