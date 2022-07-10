package com.ajijul.network.repos

import com.ajijul.network.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

open class BaseRepository {
    companion object {
        private const val TAG = "BaseRemoteRepository"
        private const val MESSAGE_KEY = "message"
        private const val ERROR_KEY = "error"
    }

    suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(IO) {
            try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(throwable)
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }
    }

    private fun convertErrorBody(throwable: HttpException): String? {

        return try {
            throwable.response()?.errorBody()?.string()?.let {
                val jsonObject = JSONObject(it)
                when {
                    jsonObject.has(MESSAGE_KEY) -> jsonObject.getString(MESSAGE_KEY)
                    jsonObject.has(ERROR_KEY) -> jsonObject.getString(ERROR_KEY)
                    else -> "Something wrong happened"
                }
            }

        } catch (e: Exception) {
            "Something wrong happened"
        }

    }
}