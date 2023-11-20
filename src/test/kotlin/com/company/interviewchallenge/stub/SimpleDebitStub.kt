package com.company.interviewchallenge.stub

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.aResponse
import com.github.tomakehurst.wiremock.client.WireMock.equalTo
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.urlMatching
import com.jayway.jsonpath.JsonPath
import java.util.UUID

class SimpleDebitStub(private val server: WireMockServer) {

    fun stubGetMerchants(responseBody: String) {
        server.stubFor(
            get(urlMatching("/merchants"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                )
        )
    }

    fun stubGerMerchantsWithError() {
        server.stubFor(
            get(urlMatching("/merchants"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                )
        )
    }

    fun stubGetMerchantById(responseBody: String) {
        val merchantId: String = JsonPath.read(responseBody, "$.id")
        server.stubFor(
            get(urlMatching("/merchants/$merchantId"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)
                )
        )
    }

    fun stubGetMerchantByIdWithError() {
        server.stubFor(
            get(urlMatching("/merchants/${UUID.randomUUID()}"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(
                    aResponse()
                        .withStatus(400)
                )
        )
    }
}