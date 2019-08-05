/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sample.currencyapp.di


import com.google.gson.Gson
import retrofit2.Response
import java.util.regex.Pattern


/**
 * Common class used by API responses.
 * @param <T> the type of the response object
</T> */
// T is used in extending classes
sealed class ApiResponse<T> {

    companion object {

        fun <T> create(response: Response<T>): ApiResponse<T> {
            val body = response.body()
            val responseBody = body
            return if (response.isSuccessful) {

                if (body == null || response.code() == 204) {
                    ApiEmptyResponse()
                } else if (response.code() != 200) {
//                    val errorMsg = ErrorResponse((responseBody as BaseDataModel).code, (responseBody as BaseDataModel).type!!
//                            , (responseBody as BaseDataModel).message!!, (responseBody as BaseDataModel).errors!!)
                    ApiErrorResponse(response.errorBody().toString())
                } else {
                    ApiSuccessResponse(body = body, linkHeader = response.headers()?.get("link"))
                }
            } else {
                val msg = response.errorBody()?.string()
                val errorMsg = if (msg.isNullOrEmpty()) {
                    val error2 = Error(response.message())
                    val error = Gson().toJson(error2)
                    error.toString()
                } else {
                    msg
                }
                val error = Gson().fromJson(errorMsg, Error::class.java)
                if (response.code() == 401)
                    ApiErrorResponse(status = Status.TOKEN_EXPIRED)
                else if (response.code() == 500) {
                    ApiErrorResponse(status = Status.INTERNAL_SERVER_ERROR)
                } else
                    ApiErrorResponse(error.status_message)
            }
        }


        fun <T> create(): ApiErrorResponse<T> {
//            val error1 = ErrorResponse(410, "", "")
            return ApiErrorResponse(status = Status.NETWORK_ERROR)
        }
    }
}


/**
 * separate class for HTTP 204 resposes so that we can make ApiSuccessResponse's body non-null.
 */
class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T, val links: Map<String, String>) : ApiResponse<T>() {

    constructor(body: T, linkHeader: String?) : this(
            body = body,
            links = linkHeader?.extractLinks() ?: emptyMap()
    )


    companion object {
        private val LINK_PATTERN = Pattern.compile("<([^>]*)>[\\s]*;[\\s]*rel=\"([a-zA-Z0-9]+)\"")

        private fun String.extractLinks(): Map<String, String> {
            val links = mutableMapOf<String, String>()
            val matcher = LINK_PATTERN.matcher(this)

            while (matcher.find()) {
                val count = matcher.groupCount()
                if (count == 2) {
                    links[matcher.group(2)] = matcher.group(1)
                }
            }
            return links
        }

    }
}

data class ApiErrorResponse<T>(val errorMessage: String? = "", val status: Status? = null) : ApiResponse<T>()