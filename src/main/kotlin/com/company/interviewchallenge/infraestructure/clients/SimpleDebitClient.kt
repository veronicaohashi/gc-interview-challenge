package com.company.interviewchallenge.infraestructure.clients

import com.company.interviewchallenge.infraestructure.exceptions.ClientException
import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.jackson.responseObject
import com.github.kittinunf.result.failure
import org.springframework.stereotype.Component

@Component
class SimpleDebitClient(
    private val baseUrl: String = "https://test.io"
) {
    fun getMerchants(): Array<String> {
        val (_, _, result) = Fuel.get("$baseUrl/merchants")
            .header("Content-Type", "application/json")
            .responseObject<Array<String>>()

        result.failure {
            throw ClientException(
                status = it.response.statusCode,
                reason = it.response.responseMessage,
                cause = it
            )
        }

        return result.get()
    }

    fun getMerchantsById(id: String): MerchantClientResponse {
        val (_, _, result) = Fuel.get("$baseUrl/merchants/$id")
            .header("Content-Type", "application/json")
            .responseObject<MerchantClientResponse>()

        result.failure {
            throw ClientException(
                status = it.response.statusCode,
                reason = it.response.responseMessage,
                cause = it
            )
        }

        return result.get()
    }
}