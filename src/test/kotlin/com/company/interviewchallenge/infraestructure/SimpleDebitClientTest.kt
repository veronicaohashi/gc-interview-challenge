package com.company.interviewchallenge.infraestructure

import com.company.interviewchallenge.IntegrationTest
import com.company.interviewchallenge.infraestructure.exception.ClientException
import org.amshove.kluent.invoking
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should contain`
import org.amshove.kluent.`should not be null`
import org.amshove.kluent.`should throw`
import org.junit.jupiter.api.Test

class SimpleDebitClientTest : IntegrationTest() {

    private val host = "http://localhost:9981"

    private val simpleDebitClient = SimpleDebitClient(host)

    @Test
    fun `should return que list of merchant ids when the request execute successfully`() {
        val expectedResponse = """[123,456,789]"""
        stub.simpleDebit.stubGetMerchants(expectedResponse)

        val response = simpleDebitClient.getMerchants()

        response.`should not be null`()
        response.size `should be equal to` 3
        response `should contain` expectedResponse
    }

    @Test
    fun `should throw ClientException when the request has an error`() {
        stub.simpleDebit.stubGerMerchantsWithError()

        invoking {
            simpleDebitClient.getMerchants()
        } `should throw` ClientException::class
    }
}