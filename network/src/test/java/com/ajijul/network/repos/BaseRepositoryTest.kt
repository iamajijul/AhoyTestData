package com.ajijul.network.repos

import com.ajijul.network.utils.ResultWrapper
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class BaseRepositoryTest {

    private var repo  = mock(BaseRepository::class.java)
    @Test
    fun `when lambda returns successfully then it should emit the result as success`() {
        runBlocking {
            val lambdaResult = true
            val result = repo.safeApiCall { lambdaResult }
            assertEquals(ResultWrapper.Success(lambdaResult), result)
        }
    }

    @Test
    fun `when lambda throws IOException then it should emit the result as NetworkError`() {
        runBlocking {
            val result = repo.safeApiCall{ throw IOException() }
            assertEquals(ResultWrapper.NetworkError, result)
        }
    }

    @Test
    fun `when lambda throws HttpException then it should emit the result as GenericError`() {
        val errorBody = "{\"error\": \"Unexpected parameter\"}".toResponseBody("application/json".toMediaTypeOrNull())

        runBlocking {
            val result = repo.safeApiCall {
                throw HttpException(Response.error<Any>(422, errorBody))
            }
            assertEquals(ResultWrapper.GenericError(422,"Unexpected parameter"), result)
        }
    }

    @Test
    fun `when lambda throws unknown exception then it should emit GenericError`() {
        runBlocking {
            val result = repo.safeApiCall {
                throw IllegalStateException()
            }
            assertEquals(ResultWrapper.GenericError(), result)
        }
    }
}